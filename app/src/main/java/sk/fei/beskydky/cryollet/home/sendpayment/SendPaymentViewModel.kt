package sk.fei.beskydky.cryollet.home.sendpayment

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.stellar.StellarHandler

data class User(val name: String)

class SendPaymentViewModel : ViewModel() {
    val currency = MutableLiveData("")
    var currencyList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    val contactName = MutableLiveData("")
    var contactList: MutableLiveData<ArrayList<String>> = MutableLiveData()


    private val _eventPaymentCompleted = MutableLiveData<Boolean>()
    val eventPaymentCompleted: LiveData<Boolean>
        get() = _eventPaymentCompleted

    private val _formState = MutableLiveData<SendPaymentFormState>()
    val formState: LiveData<SendPaymentFormState>
        get() = _formState


    val walletKey = MutableLiveData<String>()
    val amount = MutableLiveData<String>()

    val formObserver = MediatorLiveData<Boolean>()

    init {
        formObserver.addSource(walletKey) { onFormChanged() }
        formObserver.addSource(amount) { onFormChanged() }
        formObserver.addSource(currency) { onFormChanged() }
        onFormChanged()
    }


    //dump
    fun searchCurrency(name: String) {
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
        currencyList.value = list
    }

    // dump
    fun searchContacts(name: String) {
        val list = ArrayList<String>()

        list.add("Fero Pajta")
        list.add("Lukas Hajducak")
        list.add("Tanicka Smolarova")
        list.add("Bukvica")

        contactList.value = list
    }

    fun onSendPayment(){
        _eventPaymentCompleted.value = true
    }

    fun onClick() = viewModelScope.launch {
        val stellarHandler = StellarHandler()

        val source: KeyPair = KeyPair.fromSecretSeed("SCZWUQZX7AD7OENXXKNOHJXSOT2WAJOBRLVV7YNASLAMOECWTJPAC3WS")
        val destinationId: String = "GAWB5RG6F4X3SUBXYI3O3M4ZED6KFHMORIM5URKZI5BYRCJHGOO5XSLP"

        val response = stellarHandler.getPayments(source)
        Log.i("Stellar", "Success")
    }


    private fun onFormChanged(){
        var state = SendPaymentFormState()
        state.keyValid = isKeyValid()
        state.amountValid = isAmountValid()
        state.currencyValid = isCurrencyValid()
        _formState.value = state
    }

    private fun isKeyValid(): Boolean{
        return !walletKey.value.isNullOrBlank()
    }

    private fun isAmountValid(): Boolean{
        return !walletKey.value.isNullOrBlank()
    }

    private fun isCurrencyValid(): Boolean{
        return !currency.value.isNullOrBlank()
    }
}