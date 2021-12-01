package sk.fei.beskydky.cryollet.home.sendpayment

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.stellar.StellarHandler


class SendPaymentViewModel(private val balanceRepository: BalanceRepository) : ViewModel() {
    val currency = MutableLiveData("")

    private val _currencyList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val currencyList: LiveData<ArrayList<String>>
        get() = _currencyList

    val contactName = MutableLiveData("")
    var contactList: MutableLiveData<ArrayList<String>> = MutableLiveData()

    private val _eventPaymentCompleted = MutableLiveData<Boolean>()
    val eventPaymentCompleted: LiveData<Boolean>
        get() = _eventPaymentCompleted

    private val _eventScanQRCode = MutableLiveData<Boolean>()
    val eventScanQRCode: LiveData<Boolean>
        get() = _eventScanQRCode

    private val _formState = MutableLiveData<SendPaymentFormState>()
    val formState: LiveData<SendPaymentFormState>
        get() = _formState

    private val separator: String = " - "


    val walletKey = MutableLiveData<String>()
    val amount = MutableLiveData<String>()

    val formObserver = MediatorLiveData<Boolean>()

    init {
        formObserver.addSource(walletKey) { onFormChanged() }
        formObserver.addSource(amount) { onFormChanged() }
        formObserver.addSource(currency) { onFormChanged() }
        onFormChanged()

        viewModelScope.launch {
            val balances = balanceRepository.get()
            val resultList: ArrayList<String> = ArrayList()
            for (balance in balances) {
                resultList.add(formatCurrency(balance.assetName, balance.assetDescription))
            }
            _currencyList.value = resultList
        }
    }

    fun onScanQRCode() {
        _eventScanQRCode.value = true
    }

    fun onScanQRCodeFinished() {
        _eventScanQRCode.value = false
    }

    fun searchContacts(name: String) {
        val list = ArrayList<String>()

        list.add("Fero Pajta")
        list.add("Lukas Hajducak")
        list.add("Tanicka Smolarova")
        list.add("Bukvica")

        contactList.value = list
    }

    fun onSendPayment() {
        _eventPaymentCompleted.value = true
    }

    fun onClick() = viewModelScope.launch {
        val stellarHandler = StellarHandler()

        val source: KeyPair = KeyPair.fromSecretSeed("SCZWUQZX7AD7OENXXKNOHJXSOT2WAJOBRLVV7YNASLAMOECWTJPAC3WS")
        val destinationId: String = "GAWB5RG6F4X3SUBXYI3O3M4ZED6KFHMORIM5URKZI5BYRCJHGOO5XSLP"

        val response = stellarHandler.getPayments(source)
        Log.i("Stellar", "Success")
    }

    private fun formatCurrency(assetName: String, assetDescription: String): String {
        val builder = StringBuilder()
        builder.append(assetName)
                .append(separator)
                .append(assetDescription)
        return builder.toString()
    }


    private fun onFormChanged() {
        var state = SendPaymentFormState()
        state.keyValid = isKeyValid()
        state.amountValid = isAmountValid()
        state.currencyValid = isCurrencyValid()
        _formState.value = state
    }

    private fun isKeyValid(): Boolean {
        return !walletKey.value.isNullOrBlank()
    }

    private fun isAmountValid(): Boolean {
        return !walletKey.value.isNullOrBlank()
    }

    private fun isCurrencyValid(): Boolean {
        return !currency.value.isNullOrBlank()
    }
}