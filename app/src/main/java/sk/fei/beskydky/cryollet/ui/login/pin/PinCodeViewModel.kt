package sk.fei.beskydky.cryollet.ui.login.pin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import sk.fei.beskydky.cryollet.database.repository.UserRepository

class PinCodeViewModel(private val userRepository: UserRepository) : ViewModel() {

    var pinCode = String()


    var userExists = false

    private val _eventPinSucceed = MutableLiveData<Boolean>()
    val eventPinSucceed: LiveData<Boolean>
        get() = _eventPinSucceed

    private val _eventPinFails = MutableLiveData<Boolean>()
    val eventPinFails: LiveData<Boolean>
        get() = _eventPinFails


    init {
        runBlocking {

                userExists =  userExist()
                if(userExists){
                    pinCode = userRepository.getPin()!!
                }

        }
    }

    fun onPinSucceed(newPin: String?){
        if(!userExists){
            viewModelScope.launch(Dispatchers.Default) {
                userRepository.createAndInsert(newPin!!)
            }

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

    private suspend fun userExist(): Boolean {
        return userRepository.get() != null
    }

}