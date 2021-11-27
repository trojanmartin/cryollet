package sk.fei.beskydky.cryollet.home.sendpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentSendPaymentBinding

class SendPaymentFragment : Fragment() {

    private lateinit var binding: FragmentSendPaymentBinding
    private lateinit var viewModel: SendPaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_payment, container, false)
        viewModel = ViewModelProvider(this).get(SendPaymentViewModel::class.java)

        // dump currency data
        val list = resources.getStringArray(R.array.currency)

        binding.currencyAutocomplete.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, list))

//        viewModel.user.observe(viewLifecycleOwner, {
//            viewModel.searchUser(it)
//        })
        binding.viewModel = viewModel
        return binding.root

    }

}