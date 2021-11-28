package sk.fei.beskydky.cryollet.home.qrcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QrCodeViewModel : ViewModel() {
    private val _eventGenerateQRCode = MutableLiveData<Boolean>()
    val eventGenerateQRCode: LiveData<Boolean>
        get() = _eventGenerateQRCode

    fun onGenerateQRCode() {
        _eventGenerateQRCode.value = true
    }

    fun onGenerateQRCodeFinished() {
        _eventGenerateQRCode.value = false
    }
}