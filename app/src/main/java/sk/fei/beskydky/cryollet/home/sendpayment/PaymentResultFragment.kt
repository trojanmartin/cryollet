package sk.fei.beskydky.cryollet.home.sendpayment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentRequestPaymentBinding

class PaymentResultFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment_completed, container, false)
        val button = view?.findViewById<Button>(R.id.ok_button)
        button?.setOnClickListener {
            findNavController().navigate(PaymentResultFragmentDirections.actionPaymentResultFragmentToHomeFragment())
        }
        return view
    }
}