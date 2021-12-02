package sk.fei.beskydky.cryollet.home.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository

class UserInfoViewModelFactory(private val walletRepository: WalletRepository, private val userRepository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserInfoViewModel::class.java)) {
            return UserInfoViewModel(walletRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}