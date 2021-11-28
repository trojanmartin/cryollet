package sk.fei.beskydky.cryollet.ui.login.key

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import shadow.okhttp3.Dispatcher
import sk.fei.beskydky.cryollet.data.LoginRepository
import sk.fei.beskydky.cryollet.data.Result

import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import sk.fei.beskydky.cryollet.ui.login.LoggedInUserView
import sk.fei.beskydky.cryollet.ui.login.LoginFormState
import sk.fei.beskydky.cryollet.ui.login.LoginResult

class KeyLoginViewModel(private val userRepository: UserRepository,
                        private val walletRepository: WalletRepository) : ViewModel() {


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
        localWalletExist()
    }

    fun onRegister(){
        _eventOnBusy.value = true

        //TODO:  do async register
        viewModelScope.launch {
            val user = userRepository.get()
            val pin = userRepository.getPin()
            if (user != null && pin != null) {
                walletRepository.createAndInsert(user.userId, pin)
            }
            val wallet = walletRepository.get()
            if(wallet != null){
                //if success
                _eventSetUpCompleted.value = true
            }else{
                //if fail
                _eventSetUpFailed.value = true
            }
        }

        _eventOnBusy.value = false
    }

    fun onLogin() {
        _eventOnBusy.value = true

        //TODO:  do async login

        //if success
        _eventSetUpCompleted.value = true

        //if fail
        _eventSetUpFailed.value = true

        _eventOnBusy.value = false
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

    private fun localWalletExist(){
        viewModelScope.launch {
            _walletExist.value = walletRepository.get() != null
        }
    }


}