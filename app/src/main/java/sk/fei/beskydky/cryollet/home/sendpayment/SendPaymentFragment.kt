package sk.fei.beskydky.cryollet.home.sendpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.stellar.sdk.responses.AccountResponse
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.contacts.ContactsViewModel
import sk.fei.beskydky.cryollet.contacts.ContactsViewModelFactory
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository
import sk.fei.beskydky.cryollet.databinding.ContactsFragmentBinding
import sk.fei.beskydky.cryollet.databinding.FragmentSendPaymentBinding
import sk.fei.beskydky.cryollet.home.qrcode.QrCodeFragmentArgs
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick
import sk.fei.beskydky.cryollet.stellar.StellarHandler

class SendPaymentFragment : Fragment() {

    private lateinit var viewModel: SendPaymentViewModel
    private lateinit var binding : FragmentSendPaymentBinding
    private lateinit var currencyList : ArrayList<String>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_send_payment, container, false)

        val sendPaymentButton = binding.payButton
        val indicator = binding.busyIndicator

        val application = requireNotNull(this.activity).application
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val stellarDataSource = StellarHandler.getInstance(application)
        val viewModelFactory = SendPaymentViewModelFactory(BalanceRepository.getInstance(databaseDataSource, stellarDataSource),
                TransactionRepository.getInstance(databaseDataSource, stellarDataSource), ContactsRepository.getInstance(databaseDataSource), databaseDataSource)

        viewModel = ViewModelProvider(this, viewModelFactory)[SendPaymentViewModel::class.java]


        viewModel.currencyList.observe(viewLifecycleOwner, Observer {
            binding.currencyAutocomplete.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, it))
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.sendPaymentContact.setOnItemClickListener { _, _, i, _ ->
            viewModel.onItemSelectedHandler()
        }

        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            val contacts = viewModel.formatContact(it)
            binding.sendPaymentContact.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, contacts))
        })


        binding.sendPaymentAmount.doAfterTextChanged {
            if (it?.length == 2 && "00" == it.toString()) {
                binding.sendPaymentAmount.text.clear()
            }
        }

        val qrResponseData = SendPaymentFragmentArgs.fromBundle(requireArguments())
        if(qrResponseData.qrCodeResult != "") {
            val resolvedResult : List<String> = qrResponseData.qrCodeResult.split(',')
            viewModel.amount.value = resolvedResult[0]
            viewModel.currency.value = resolvedResult[1]
            viewModel.walletKey.value = resolvedResult[2]
        }

        viewModel.eventScanQRCode.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController()
                        .navigate(
                                SendPaymentFragmentDirections
                                        .actionSendPaymentFragmentToQrCodeScannerFragment())
            }
        })

        viewModel.eventPaymentCompleted.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(SendPaymentFragmentDirections.actionSendPaymentFragmentToPaymentResultFragment())
                viewModel.onPaymentCompletedFinished()
            }
        })

        viewModel.eventPaymentStarted.observe(viewLifecycleOwner, Observer {
            if (it) {
                indicator.show()
            } else {
                indicator.hide()
            }
        })

        viewModel.formState.observe(viewLifecycleOwner, Observer {
            sendPaymentButton.isEnabled = it.isValid()
        })

        viewModel.formObserver.observe(viewLifecycleOwner, {})
        binding.lifecycleOwner = this
        binding.root?.setHideKeyboardOnClick(this)

        binding.viewModel = viewModel
        return binding.root

    }

}