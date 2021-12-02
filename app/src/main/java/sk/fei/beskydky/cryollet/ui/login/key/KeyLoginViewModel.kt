package sk.fei.beskydky.cryollet.ui.login.key

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.ui.login.LoginFormState

class KeyLoginViewModel(private val userRepository: UserRepository,
                        private val walletRepository: WalletRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    private val _walletExist = MutableLiveData<Boolean>()
    val walletExist: LiveData<Boolean>
        get() = _walletExist

    val key = MutableLiveData<String>()

    private val _eventSetUpCompleted = MutableLiveData<Boolean>()
    val eventSetUpCompleted: LiveData<Boolean>
        get() = _eventSetUpCompleted

    private val _eventSetUpFailed = MutableLiveData<Boolean>()
    val eventSetUpFailed: LiveData<Boolean>
        get() = _eventSetUpFailed

    private val _eventOnBusy = MutableLiveData<Boolean>()
    val eventOnBusy: LiveData<Boolean>
        get() = _eventOnBusy

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    init{

    }

    fun onRegister(){
        _eventOnBusy.value = true

        //TODO:  do async register
        viewModelScope.launch(Dispatchers.Main) {
            val user = userRepository.get()
            val pin = userRepository.getPin()
            if (user != null && pin != null) {
                try {
                    walletRepository.createNewAndInsert(user.userId, pin)
                } catch (e: Exception) {
                    errorMessage.value = e.message
                }
            }
            val wallet = walletRepository.get()
            if(wallet != null){
                //if success
                _eventSetUpCompleted.value = true
            }else{
                //if fail
                _eventSetUpFailed.value = true
            }
            _eventOnBusy.value = false
        }


    }

    fun onLogin() {
        _eventOnBusy.value = true

        viewModelScope.launch {
            val user = userRepository.get()
            var result = false
            val pin = userRepository.getPin()
            if (user != null && pin != null) {
                try {
                    result =
                        walletRepository.createFromSecretAndInsert(key.value!!, user.userId, pin)
                } catch (e: Exception) {
                    errorMessage.value = e.message
                    result = false
                }
            }

            if(result){
                //if success
                _eventSetUpCompleted.value = true
            }else{
                //if fail
                _eventSetUpFailed.value = true
            }
            _eventOnBusy.value = false
        }


    }



    fun loginDataChanged(username: String) {
        if (!isKeyValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
        else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isKeyValid(key: String): Boolean {
        return key.length > 10
    }
    


}