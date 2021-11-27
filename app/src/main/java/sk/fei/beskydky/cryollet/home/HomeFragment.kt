package sk.fei.beskydky.cryollet.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.hideKeyboard

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.setOnClickListener {
            hideKeyboard()
        }
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


}