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
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentRequestPaymentBinding


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
        viewModel = ViewModelProvider(this)[RequestPaymentViewModel::class.java]

        viewModel.eventCancelledDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                dismiss()
                viewModel.onRequestPaymentCancelFinished()
            }
        })

        viewModel.eventApproveDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.root.findNavController().navigate(RequestPaymentFragment)
                viewModel.onRequestPaymentApproveFinished()
            }
        })

        binding.viewModel = viewModel
        return binding.root
    }

}