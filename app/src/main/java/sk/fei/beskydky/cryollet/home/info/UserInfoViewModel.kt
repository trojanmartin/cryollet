package sk.fei.beskydky.cryollet.home.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository

class UserInfoViewModel(private val walletRepository: WalletRepository,
                        private val userRepository: UserRepository,
                        private val database: AppDatabaseDao) : ViewModel() {

    private val _eventCopyPublicClicked = MutableLiveData<Boolean>()
    val eventCopyPublicClicked: LiveData<Boolean>
        get() = _eventCopyPublicClicked


    private val _eventCopySecretClicked = MutableLiveData<Boolean>()
    val eventCopySecretClicked: LiveData<Boolean>
        get() = _eventCopySecretClicked

    val balances = database.getAllBalancesLiveData()
    var secretKey = MutableLiveData<String>()
    var publicKey = MutableLiveData<String>()


    init {
        viewModelScope.launch {
            publicKey.value = walletRepository.get()?.accountId ?: "Not availaible"
            secretKey.value = walletRepository.getSecretKey(userRepository.getPin()!!)
        }
    }


    fun onCopyPublic() {
        _eventCopyPublicClicked.value = true
    }

    fun onCopyPublicFinished() {
        _eventCopyPublicClicked.value = false
    }

    fun onCopySecret() {
        _eventCopySecretClicked.value = true
    }

    fun onCopySecretFinished() {
        _eventCopySecretClicked.value = false
    }
}