package sk.fei.beskydky.cryollet.home.sendpayment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentSendPaymentBinding
import sk.fei.beskydky.cryollet.hideKeyboard


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
        val currency = resources.getStringArray(R.array.currency)

        // dump contacts data
        val contacts = resources.getStringArray(R.array.contancts_names)

        binding.currencyAutocomplete.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, currency))
        binding.sendPaymentContact.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, contacts))

        binding.sendPaymentAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }
        })

        viewModel.user.observe(viewLifecycleOwner, Observer{
            viewModel.searchCurrency(it)
        })

        viewModel.contactName.observe(viewLifecycleOwner, Observer {
            viewModel.searchContacts(it)
        })

        view?.setOnClickListener {
            hideKeyboard()
        }

        binding.viewModel = viewModel
        return binding.root

    }

}