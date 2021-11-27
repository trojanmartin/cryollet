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

class KeyLoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val key = MutableLiveData<String>()

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login() {
        // can be launched in a separate asynchronous job
        val currentKey = key?.value ?: ""
        val result = loginRepository.login(currentKey, "")

        _loginResult.value =  LoginResult(LoggedInUserView(currentKey), null)
//        if (result is Result.Success) {
//            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String) {
        if (!isKeyValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        }
        else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isKeyValid(key: String): Boolean {
        return key.length > 10
    }
}