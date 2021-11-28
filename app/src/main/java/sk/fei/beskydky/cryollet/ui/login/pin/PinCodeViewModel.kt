package sk.fei.beskydky.cryollet.ui.login.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PinCodeViewModel() : ViewModel() {

    var pinCode = String()
    var setUpPin = !userExist()

    private val _eventPinSucceed = MutableLiveData<Boolean>()
    val eventPinSucceed: LiveData<Boolean>
        get() = _eventPinSucceed

    private val _eventPinFails = MutableLiveData<Boolean>()
    val eventPinFails: LiveData<Boolean>
        get() = _eventPinFails


    init {
        if(!setUpPin){
            pinCode = "1234" //TODO: Load from DB
        }
    }

    fun onPinSucceed(newPin: String?){
        if(setUpPin){
            // TODO: Create user into db with new  PIN
        }
        _eventPinSucceed.value = true
    }

    fun onPinSucceedFinished(){
        _eventPinSucceed.value = false
    }

    fun onPinFails(){
        _eventPinFails.value = true
    }

    fun onPinFailsFinished(){
        _eventPinFails.value = false
    }

    private fun userExist(): Boolean{
        return false //TODO: Call repo
    }
}