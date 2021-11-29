package sk.fei.beskydky.cryollet.home.requestpayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository

class RequestPaymentViewModel(private val walletRepository: WalletRepository) : ViewModel() {
    private val _eventCancelledDialog = MutableLiveData<Boolean>()
    val eventCancelledDialog: LiveData<Boolean>
        get() = _eventCancelledDialog

    private val _eventApproveDialog = MutableLiveData<Boolean>()
    val eventApproveDialog: LiveData<Boolean>
        get() = _eventApproveDialog

    private val requestPaymentAmount = MutableLiveData<Int>()
    private val requestPaymentCurrency = MutableLiveData<String>()
    private val publicKey = MutableLiveData<String>()

    private val separator = ','

    init {
        requestPaymentAmount.value = 0
        requestPaymentCurrency.value = "EUR"
    }

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

    fun getDataToGenerateQRCode(): String {
        val dataToQRCode = StringBuilder()
        viewModelScope.launch {
            publicKey.value = walletRepository.get()?.accountId.toString()
        }
        dataToQRCode.append(requestPaymentAmount.value.toString())
            .append(separator)
            .append(requestPaymentCurrency.value.toString())
            .append(separator)
            .append(publicKey)
        return dataToQRCode.toString()
    }
}