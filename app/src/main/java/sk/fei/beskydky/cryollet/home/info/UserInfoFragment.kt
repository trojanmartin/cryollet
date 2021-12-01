package sk.fei.beskydky.cryollet.home.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.copyToClipboard
import sk.fei.beskydky.cryollet.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {

    private lateinit var viewModel: UserInfoViewModel
    private lateinit var binding: FragmentUserInfoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)

        viewModel.eventCopyPublicClicked.observe(viewLifecycleOwner, {
            if(it){
                context?.copyToClipboard(viewModel.publicKey.value ?: "")
                showToast(R.string.copied)
                viewModel.onCopyPublicFinished()
            }
        })

        viewModel.eventCopySecretClicked.observe(viewLifecycleOwner, {
            if(it){
                context?.copyToClipboard(viewModel.secretKey.value ?: "")
                showToast(R.string.copied)
                viewModel.onCopySecretFinished()
            }
        })

        binding.okButton.setOnClickListener {
            findNavController().navigate(UserInfoFragmentDirections.actionUserInfoFragmentToHomeFragment())
        }

        binding.viewModel = viewModel
        return binding.root
    }

    private fun showToast(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }
}