package sk.fei.beskydky.cryollet.ui.login.pin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.hanks.passcodeview.PasscodeView
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import kotlinx.coroutines.*
import sk.fei.beskydky.cryollet.MainActivity
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.data.model.Wallet
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.databinding.FragmentPinCodeBinding
import java.util.*

class PinCodeFragment : Fragment() {

    private lateinit var binding: FragmentPinCodeBinding
    private lateinit var viewModel: PinCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pin_code, container, false)

        val application = requireNotNull(this.activity).application
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val viewModelFactory = PinCodeViewModelFactory(UserRepository(databaseDataSource))

        viewModel = ViewModelProvider(
                            this,
                            viewModelFactory).get(PinCodeViewModel::class.java)

        viewModel.eventPinFails.observe(viewLifecycleOwner, {
            if (it){
                Toast.makeText(requireContext(), "Password is wrong!", Toast.LENGTH_SHORT).show()
                viewModel.onPinFailsFinished()
            }
        })

        viewModel.eventPinSucceed.observe(viewLifecycleOwner, {
            if(it){
             var wallet = Wallet(userId = 0L, walletId = 0L, accountId = "", secretKey = "", balance = 0.0)
                runBlocking {
                    var wallet = databaseDataSource.getWallet()
                }
                if(wallet != null){
                    navigateToMainActivity()
                }else{
                    binding.root.findNavController().navigate(PinCodeFragmentDirections.actionPinCodeFragmentToKeyLoginFragment())
                    viewModel.onPinSucceedFinished()
                }

            }
        })


        setUpPinView(binding, viewModel)
        return binding.root
    }

    private fun setUpPinView(binding: FragmentPinCodeBinding, viewModel: PinCodeViewModel){
        var type = PasscodeView.PasscodeViewType.TYPE_CHECK_PASSCODE
        var headerText = getString(R.string.insert_pin)


        if(!viewModel.userExists){
            type = PasscodeView.PasscodeViewType.TYPE_SET_PASSCODE
            headerText = getString(R.string.set_up_pin)
        }
        else{
            binding.passcodeView.localPasscode = viewModel.pinCode
        }

        binding.passcodeView.firstInputTip = headerText
        binding.passcodeView.passcodeType = type

        binding.passcodeView.listener = object : PasscodeViewListener {
            override fun onFail() {
                viewModel.onPinFails()
            }

            override fun onSuccess(number: String?) {
                viewModel.onPinSucceed(number)
            }
        }
    }
    private fun navigateToMainActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
    }
}