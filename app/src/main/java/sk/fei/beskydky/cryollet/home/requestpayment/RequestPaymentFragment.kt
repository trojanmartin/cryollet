package sk.fei.beskydky.cryollet.home.requestpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentRequestPaymentBinding


class RequestPaymentFragment : DialogFragment() {

    private lateinit var binding : FragmentRequestPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_payment, container, false)

        return binding.root
    }

}