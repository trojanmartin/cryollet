package sk.fei.beskydky.cryollet.home.sendpayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentSendPaymentBinding
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick

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

        val sendPaymentButton = binding.payButton

        // dump currency data
        val currency = resources.getStringArray(R.array.currency)

        // dump contacts data
        val contacts = resources.getStringArray(R.array.contancts_names)

        binding.currencyAutocomplete.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, currency))
        binding.sendPaymentContact.setAdapter(ArrayAdapter(requireContext(), R.layout.currency_dropdown_item, contacts))

        binding.sendPaymentAmount.doAfterTextChanged {
            if(it?.length == 2 && "00" == it.toString()) {
                binding.sendPaymentAmount.text.clear()
            }
        }

        viewModel.currency.observe(viewLifecycleOwner, Observer{
            viewModel.searchCurrency(it)
        })

        viewModel.contactName.observe(viewLifecycleOwner, Observer {
            viewModel.searchContacts(it)
        })

        viewModel.eventPaymentCompleted.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(SendPaymentFragmentDirections.actionSendPaymentFragmentToPaymentResultFragment())
        })

        viewModel.formState.observe(viewLifecycleOwner, Observer {
            sendPaymentButton.isEnabled = it.isValid()
        })

        viewModel.formObserver.observe(viewLifecycleOwner,{})

        binding.root?.setHideKeyboardOnClick(this)

        binding.viewModel = viewModel
        return binding.root

    }

}