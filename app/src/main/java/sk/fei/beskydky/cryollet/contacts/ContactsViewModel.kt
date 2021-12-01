package sk.fei.beskydky.cryollet.contacts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository

class ContactsViewModel(contactsRepository : ContactsRepository) : ViewModel() {

    private var _contacts = MutableLiveData<MutableList<Contact>>()
    val contacts: LiveData<MutableList<Contact>>
        get() = _contacts

    init {
        viewModelScope.launch {
            //transRepo.makeTransaction("GA3KRKTFY5XFKNA6TGDJYRLDIGVJ4XUDOB3E6ZWOHS46HTSQUNTF544D", "10", "test")
            _contacts.value = contactsRepository.get()
        }
    }
}

