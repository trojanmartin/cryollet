package sk.fei.beskydky.cryollet.ui.login.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinCodeViewModel(publicKey: String) : ViewModel() {
    private val _ifUserHaveAlreadyPin = MutableLiveData<Boolean>()
    val ifUserHaveAlreadyPin: LiveData<Boolean>
        get() = _ifUserHaveAlreadyPin

    var key = publicKey
    
    init {
        var user = db.getUser(key)
        checkIfPinSet(user)
    }

    fun getUserPIN(): String {
        // TODO: Resource get PIN from DB
        return "1234"
    }

    fun checkIfPinSet(user: User) {
        // TODO: Get user by his public key
        // User user = db.getUser(publicKey)
        _ifUserHaveAlreadyPin.value = user != null
    }


}