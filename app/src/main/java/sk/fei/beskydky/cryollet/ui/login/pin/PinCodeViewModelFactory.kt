package sk.fei.beskydky.cryollet.ui.login.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.ui.login.key.KeyLoginViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class PinCodeViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PinCodeViewModel::class.java)) {
            return PinCodeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}