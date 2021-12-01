package sk.fei.beskydky.cryollet.home.requestpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.databinding.FragmentRequestPaymentBinding
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeViewModel
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeViewModelFactory


class RequestPaymentFragment : DialogFragment() {

    private lateinit var binding: FragmentRequestPaymentBinding
    private lateinit var viewModel: RequestPaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_request_payment, container, false)

        val application = requireNotNull(this.activity).application
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val stellarDataSource = StellarHandler.getInstance(application)
        val viewModelFactory =
            RequestPaymentViewModelFactory(WalletRepository.getInstance(databaseDataSource, stellarDataSource))

        viewModel = ViewModelProvider(this, viewModelFactory)[RequestPaymentViewModel::class.java]

        viewModel.eventCancelledDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                dismiss()
                viewModel.onRequestPaymentCancelFinished()
            }
        })

        viewModel.eventApproveDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                val dataToQRCode = viewModel.getDataToGenerateQRCode()
                findNavController(this)
                    .navigate(
                        RequestPaymentFragmentDirections
                            .actionRequestPaymentFragmentToQrCodeFragment(dataToQRCode)
                    )
                viewModel.onRequestPaymentApproveFinished()
            }
        })

        binding.viewModel = viewModel
        return binding.root
    }

}