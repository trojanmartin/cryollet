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
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.HomeFragmentBinding
import sk.fei.beskydky.cryollet.home.requestpayment.RequestPaymentFragment


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
                val dialog = RequestPaymentFragment()
                dialog.show(myContext.supportFragmentManager, "requestPaymentDialog")
                viewModel.onRequestPaymentFinished()
            }
        })
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity

    }


}