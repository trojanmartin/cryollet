package sk.fei.beskydky.cryollet.contacts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository

class ContactsViewModel(databaseDao: AppDatabaseDao) : ViewModel() {
    val contacts = databaseDao.getAllContactsLiveData()
}

