package sk.fei.beskydky.cryollet.home

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.stellar.StellarHandler
import sk.fei.beskydky.cryollet.models.User


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val policy = ThreadPolicy.Builder()
            .permitAll().build()
        StrictMode.setThreadPolicy(policy)
        initializeUser()
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    private fun initializeUser() {
        var keyPair : KeyPair = StellarHandler.createWallet()
        var newUser: User = User(secretKey = keyPair.accountId, publicKey = keyPair.publicKey.toString(), pin = "6696")
        Log.e("TAG",keyPair.accountId)
    }
}