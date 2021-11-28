package sk.fei.beskydky.cryollet.ui.login.key

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import sk.fei.beskydky.cryollet.MainActivity
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.contacts.ContactsViewModelFactory
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.databinding.KeyLoginFragmentBinding
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import sk.fei.beskydky.cryollet.ui.login.LoggedInUserView

class KeyLoginFragment : Fragment() {

    private lateinit var viewModel: KeyLoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: KeyLoginFragmentBinding = DataBindingUtil.inflate(
                                                                    inflater,
                                                                    R.layout.key_login_fragment,
                                                                    container,
                                                                    false)

        val username = binding.accountKey
        val login = binding.signInButton
        // disable onLogin button unless both username / password is valid
        login.isEnabled = false

        val application = requireNotNull(this.activity).application
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val stellarDataSource = StellarHandler.getInstance(application)
        val viewModelFactory = KeyLoginViewModelFactory(
                                            UserRepository(databaseDataSource),
                                            WalletRepository(databaseDataSource, stellarDataSource))

        viewModel = ViewModelProvider(
            this,
            viewModelFactory).get(KeyLoginViewModel::class.java)

        viewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable onLogin button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
        })

        viewModel.eventOnBusy.observe(viewLifecycleOwner, Observer {
            //TODO: Spinner
        })

        viewModel.eventSetUpCompleted.observe(viewLifecycleOwner, Observer {
            if(it){
               navigateToMainActivity()
            }
        })

        viewModel.eventSetUpFailed.observe(viewLifecycleOwner, Observer {
            if(it){
                showLoginFailed(R.string.login_failed)
            }
        })

        username.afterTextChanged {
            viewModel.loginDataChanged(
                username.text.toString()
            )
        }

        binding.viewModel = viewModel
        binding.root.setHideKeyboardOnClick(this)
        return binding.root
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}