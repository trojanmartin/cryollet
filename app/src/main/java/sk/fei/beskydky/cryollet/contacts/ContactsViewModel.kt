package sk.fei.beskydky.cryollet.contacts


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.UserRepository

class ContactsViewModel(database:AppDatabaseDao,application: Application) : AndroidViewModel (application) {

    private var userRepository: UserRepository = UserRepository(database)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        initUser()
    }

    private fun initUser() {

        viewModelScope.launch {
            _user.value = userRepository.get()
            if(user.value == null){
               userRepository.createAndInsert()
                _user.value = userRepository.get()
            }
        }
    }
}

class ContactsViewModelFactory(
    private val dataSource: AppDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

