package sk.fei.beskydky.cryollet.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _eventRequestPaymentClicked = MutableLiveData<Boolean>()
    val eventRequestPaymentClicked: LiveData<Boolean>
        get() = _eventRequestPaymentClicked

    private val _eventSendPaymentClicked = MutableLiveData<Boolean>()
    val eventSendPaymentClicked: LiveData<Boolean>
        get() = _eventSendPaymentClicked

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
}