package sk.fei.beskydky.cryollet.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.HomeFragmentBinding
import sk.fei.beskydky.cryollet.home.requestpayment.RequestPaymentFragment
import sk.fei.beskydky.cryollet.setHideKeyboardOnClick

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel

    private lateinit var myContext: FragmentActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.eventRequestPaymentClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                //val dialog = RequestPaymentFragment()
                //dialog.show(myContext.supportFragmentManager, "requestPaymentDialog")
                val data = "jakub"
                NavHostFragment.findNavController(this)
                    .navigate(
                        HomeFragmentDirections
                            .actionHomeFragmentToSendPaymentFragment()
                    )
                viewModel.onRequestPaymentFinished()
            }
        })

        viewModel.eventSendPaymentClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                //TODO:  dokoncit navigaciu na send payment fragment
                viewModel.onSendPaymentFinished()
            }
        })
        binding.viewModel = viewModel
        binding.root.setHideKeyboardOnClick(this)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }
}