package sk.fei.beskydky.cryollet.home.qrcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

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