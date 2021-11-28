package sk.fei.beskydky.cryollet.ui.login.key

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sk.fei.beskydky.cryollet.data.LoginRepository
import sk.fei.beskydky.cryollet.data.Result

import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.ui.login.LoggedInUserView
import sk.fei.beskydky.cryollet.ui.login.LoginFormState
import sk.fei.beskydky.cryollet.ui.login.LoginResult

class KeyLoginViewModel() : ViewModel() {

    val userExist = userExist()
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
        if(userExist()){
            key.value = getUserKey()
        }
    }

    fun onRegister(){
        _eventOnBusy.value = true

        //TODO:  do async register


        //if success
        _eventSetUpCompleted.value = true

        //if fail
        _eventSetUpFailed.value = true

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

    private fun userExist(): Boolean{
        return true //TODO: Call repo
    }

    private fun getUserKey(): String{
        return "saaaaaaaa" //TODO: Call repo
    }
}