package sk.fei.beskydky.cryollet.home.sendpayment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import org.stellar.sdk.KeyPair

data class User(val name: String)

class SendPaymentViewModel : ViewModel() {
    val user = MutableLiveData("")
    var userList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    //dump
    fun searchUser(name: String) {
        val list = ArrayList<String>()

        list.add("CZK - Czech Koruna")
        list.add("DKK - Danish Krone")
        list.add("DOP - Dominican Peso")
        list.add("EUR - Euro")
        list.add("ALL - Albanian Lek")
        list.add("AMD - Armenian Dram")
        list.add("AOA - Angolan Kwanza")
        list.add("ARS - Argentine Peso")
        list.add("PLN - Polish Zloty")
        list.add("PYG - Paraguayan Guarani")
        list.add("QAR - Qatari Riyal")
        list.add("RON - Romanian Leu")
        userList.value = list
    }

    //test
    fun onClick() {
        val stellarHandler = StellarHandler()

        val key: KeyPair = KeyPair.fromAccountId("GAWB5RG6F4X3SUBXYI3O3M4ZED6KFHMORIM5URKZI5BYRCJHGOO5XSLP")

        val balance = viewModelScope.launch { stellarHandler.getBalances(key) }

        Log.i("stellar", balance.toString())
    }
}