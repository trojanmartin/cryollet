package sk.fei.beskydky.cryollet.ui.login.key

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.data.LoginDataSource
import sk.fei.beskydky.cryollet.data.LoginRepository
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class KeyLoginViewModelFactory(
    private val userRepository: UserRepository,
    private val walletRepository: WalletRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(KeyLoginViewModel::class.java)) {
            return KeyLoginViewModel(userRepository, walletRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}