package sk.fei.beskydky.cryollet.home.requestpayment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository

class RequestPaymentViewModel(private val walletRepository: WalletRepository,
                              private val balanceRepository: BalanceRepository) : ViewModel() {
    private val _eventCancelledDialog = MutableLiveData<Boolean>()
    val eventCancelledDialog: LiveData<Boolean>
        get() = _eventCancelledDialog

    private val _eventApproveDialog = MutableLiveData<Boolean>()
    val eventApproveDialog: LiveData<Boolean>
        get() = _eventApproveDialog

    val requestPaymentAmount = MutableLiveData<String>()
    val requestPaymentCurrency = MutableLiveData<String>()
    private val publicKey = MutableLiveData<String>()

    private val _currencyList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    val currencyList: LiveData<ArrayList<String>>
        get() = _currencyList

    private val separator = ','
    private val separatorDash = " - "

    init {
        viewModelScope.launch {
            publicKey.value = walletRepository.get()?.accountId.toString()

            val balances = balanceRepository.get()
            val resultList: ArrayList<String> = ArrayList()
            for (balance in balances) {
                resultList.add(formatCurrency(balance.assetName, balance.assetDescription))
            }
            _currencyList.value = resultList
        }
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
        dataToQRCode.append(requestPaymentAmount.value.toString())
            .append(separator)
            .append(requestPaymentCurrency.value)
            .append(separator)
            .append(publicKey.value)
        return dataToQRCode.toString()
    }

    private fun formatCurrency(assetName: String, assetDescription: String): String {
        val builder = StringBuilder()
        builder.append(assetName)
                .append(separatorDash)
                .append(assetDescription)
        return builder.toString()
    }
}