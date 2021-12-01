package sk.fei.beskydky.cryollet.home.requestpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.databinding.FragmentRequestPaymentBinding
import sk.fei.beskydky.cryollet.stellar.StellarHandler


interface CustomDialogInterface {

    fun okButtonClicked(value: String?)
}

class RequestPaymentFragment(var listener: CustomDialogInterface) : DialogFragment(), View.OnClickListener {

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
                RequestPaymentViewModelFactory(WalletRepository(databaseDataSource, stellarDataSource),
                        BalanceRepository(databaseDataSource, stellarDataSource))

        viewModel = ViewModelProvider(this, viewModelFactory)[RequestPaymentViewModel::class.java]

        viewModel.eventCancelledDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                dismiss()
                viewModel.onRequestPaymentCancelFinished()
            }
        })

        viewModel.currencyList.observe(viewLifecycleOwner, Observer {
            binding.currencyAutocomplete.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, it))
        })

        binding.approvePaymentButton.setOnClickListener(this)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onClick(p0: View?) {
        listener.okButtonClicked(viewModel.getDataToGenerateQRCode())
        viewModel.onRequestPaymentApproveFinished()
        dismiss()
    }

}