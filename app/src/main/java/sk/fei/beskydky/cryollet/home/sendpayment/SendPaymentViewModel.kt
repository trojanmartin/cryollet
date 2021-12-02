package sk.fei.beskydky.cryollet.home.sendpayment

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository
import sk.fei.beskydky.cryollet.stellar.StellarHandler


class SendPaymentViewModel(private val balanceRepository: BalanceRepository,
                           private val transactionRepository: TransactionRepository,
                           private val contactsRepository: ContactsRepository,
                           private val database: AppDatabaseDao) : ViewModel() {
    val currency = MutableLiveData("")
    val contactName = MutableLiveData<String>("")

    val errorMessage = MutableLiveData<String>()

    private val _currencyList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val currencyList: LiveData<ArrayList<String>>
        get() = _currencyList

    private val _eventPaymentCompleted = MutableLiveData<Boolean>()
    val eventPaymentCompleted: LiveData<Boolean>
        get() = _eventPaymentCompleted

    private val _eventPaymentStarted = MutableLiveData<Boolean>()
    val eventPaymentStarted: LiveData<Boolean>
        get() = _eventPaymentStarted


    private val _eventScanQRCode = MutableLiveData<Boolean>()
    val eventScanQRCode: LiveData<Boolean>
        get() = _eventScanQRCode

    private val _formState = MutableLiveData<SendPaymentFormState>()
    val formState: LiveData<SendPaymentFormState>
        get() = _formState

    val contacts = database.getAllContactsLiveData()


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
                resultList.add(balance.assetName)
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

    fun onSendPayment() {
        viewModelScope.launch {
            _eventPaymentStarted.value = true

            if((contactName.value?.length) ?: -1  > 0){
                contactsRepository.insertReplace(Contact(walletId = walletKey.value!!, contactName.value!!))
            }
            try {
                transactionRepository.makeTransaction(destinationId = walletKey.value!!, amount = amount.value!!, assetCode = currency.value!!)
            } catch (e: Exception) {
                errorMessage.value = e.message
                _eventPaymentStarted.value = false
                return@launch
            }
            _eventPaymentStarted.value = false
            _eventPaymentCompleted.value = true
        }

    }

    fun onPaymentCompletedFinished(){
        _eventPaymentCompleted.value = false
    }

    fun formatContact(listOfContacts: MutableList<Contact>) : List<String> {
        val contacts = ArrayList<String>()

        for (c in listOfContacts) {
            contacts.add(c.name)
        }
        return contacts
    }

    fun onItemSelectedHandler() {
        if(contactName.value != "" && contactName.value != null) {
            viewModelScope.launch {
                val contact = database.getContactByName(contactName.value ?: "")
                walletKey.value = contact?.walletId ?: ""
            }
        }
    }


    private fun onFormChanged() {
        var state = SendPaymentFormState()
        state.keyValid = isKeyValid()
        state.amountValid = isAmountValid()
        state.currencyValid = isCurrencyValid()
        _formState.value = state
    }

    private fun isKeyValid(): Boolean {
        val data = walletKey.value ?: return false
        return data.startsWith("G")
                && data.length >= 56
    }

    private fun isAmountValid(): Boolean {
        return !walletKey.value.isNullOrBlank()
    }

    private fun isCurrencyValid(): Boolean {
        return !currency.value.isNullOrBlank()
    }
}