package sk.fei.beskydky.cryollet.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _eventRequestPaymentClicked = MutableLiveData<Boolean>()
    val eventRequestPaymentClicked: LiveData<Boolean>
        get() = _eventRequestPaymentClicked

    fun onRequestPayment() {
        _eventRequestPaymentClicked.value = true
    }

    fun onRequestPaymentFinished() {
        _eventRequestPaymentClicked.value = false
    }
}