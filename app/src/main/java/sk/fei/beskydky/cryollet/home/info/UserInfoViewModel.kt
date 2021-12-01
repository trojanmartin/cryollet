package sk.fei.beskydky.cryollet.home.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel() : ViewModel() {

    private val _eventCopyPublicClicked = MutableLiveData<Boolean>()
    val eventCopyPublicClicked: LiveData<Boolean>
        get() = _eventCopyPublicClicked


    private val _eventCopySecretClicked = MutableLiveData<Boolean>()
    val eventCopySecretClicked: LiveData<Boolean>
        get() = _eventCopySecretClicked


    val secretKey = MutableLiveData<String>()
    val publicKey = MutableLiveData<String>()

    init {
        secretKey.value = "asdddddddddddddd"
        publicKey.value = "bbbbbbbbbbbbbbbbbbbbbbbbbb"
    }


    fun onCopyPublic() {
        _eventCopyPublicClicked.value = true
    }

    fun onCopyPublicFinished() {
        _eventCopyPublicClicked.value = false
    }

    fun onCopySecret() {
        _eventCopySecretClicked.value = true
    }

    fun onCopySecretFinished() {
        _eventCopySecretClicked.value = false
    }
}