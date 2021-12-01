package sk.fei.beskydky.cryollet.ui.login.key

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.MainActivity
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.afterTextChanged
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.databinding.KeyLoginFragmentBinding
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick
import sk.fei.beskydky.cryollet.stellar.StellarHandler

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
            var indicator = binding.busyIndicator
            if (it) {
                indicator.show()
            } else {
                indicator.hide()
            }
        })
        viewModel.walletExist.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToMainActivity()
            }
        })
        viewModel.eventSetUpCompleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                navigateToMainActivity()
            }
        })

        viewModel.eventSetUpFailed.observe(viewLifecycleOwner, Observer {
            if (it) {
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

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}