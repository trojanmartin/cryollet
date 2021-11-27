package sk.fei.beskydky.cryollet.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.repository.UserRepository

class ContactsViewModel(private val repository: UserRepository) : ViewModel() {


}

class ContactsViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}