package sk.fei.beskydky.cryollet.home.sendpayment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.stellar.StellarHandler

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
    @DelicateCoroutinesApi
    fun onClick() {
        val stellarHandler = StellarHandler()
        GlobalScope.launch {
            stellarHandler.createAccount()
//        sk.fei.beskydky.cryollet.database.stellar.StellarHandler.createWallet()
        }
    }
}