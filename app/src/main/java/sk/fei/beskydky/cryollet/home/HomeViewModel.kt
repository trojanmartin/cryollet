package sk.fei.beskydky.cryollet.home

import androidx.lifecycle.*
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao

class HomeViewModel(private val database: AppDatabaseDao) : ViewModel() {
    private val _eventRequestPaymentClicked = MutableLiveData<Boolean>()
    val eventRequestPaymentClicked: LiveData<Boolean>
        get() = _eventRequestPaymentClicked

    private val _eventSendPaymentClicked = MutableLiveData<Boolean>()
    val eventSendPaymentClicked: LiveData<Boolean>
        get() = _eventSendPaymentClicked

    private val _eventInfoClicked = MutableLiveData<Boolean>()
    val eventInfoClicked: LiveData<Boolean>
        get() = _eventInfoClicked

     val transactions = database.getAllTransactionsLiveData()
     val balances = database.getAllBalancesLiveData()


    fun onRequestPayment() {
        _eventRequestPaymentClicked.value = true
    }

    fun onRequestPaymentFinished() {
        _eventRequestPaymentClicked.value = false
    }

    fun onSendPayment() {
        _eventSendPaymentClicked.value = true
    }

    fun onSendPaymentFinished() {
        _eventSendPaymentClicked.value = false
    }

    fun onInfoClicked() {
        _eventInfoClicked.value = true
    }

    fun onInfoClickedFinished() {
        _eventInfoClicked.value = false
    }
}