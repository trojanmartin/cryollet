package sk.fei.beskydky.cryollet.ui.login.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.databinding.FragmentPinCodeBinding

class PinCodeFragment : Fragment() {

    private lateinit var binding: FragmentPinCodeBinding
    private lateinit var viewModel: PinCodeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pin_code, container, false)
        viewModel = ViewModelProvider(this)[PinCodeViewModel::class.java]

        processPinCode()
        return binding.root
    }

    private fun processPinCode() {
        binding.passcodeView.localPasscode = viewModel.getUserPIN()

        binding.passcodeView.listener = object : PasscodeViewListener {
            override fun onFail() {
                Toast.makeText(requireContext(), "Password is wrong!", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(number: String?) {
                Toast.makeText(requireContext(), "Password is correct!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}