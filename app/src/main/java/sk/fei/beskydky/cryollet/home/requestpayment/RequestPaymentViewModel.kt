package sk.fei.beskydky.cryollet.home.requestpayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RequestPaymentViewModel : ViewModel() {
    private val _eventCancelledDialog = MutableLiveData<Boolean>()
    val eventCancelledDialog: LiveData<Boolean>
        get() = _eventCancelledDialog

    private val _eventApproveDialog = MutableLiveData<Boolean>()
    val eventApproveDialog: LiveData<Boolean>
        get() = _eventApproveDialog

    fun onRequestPaymentCancel() {
        _eventCancelledDialog.value = true
    }

    fun onRequestPaymentCancelFinished() {
        _eventCancelledDialog.value = false
    }

    fun onRequestPaymentApprove() {
        _eventApproveDialog.value = true
    }

    fun onRequestPaymentApproveFinished() {
        _eventApproveDialog.value = false
    }
}