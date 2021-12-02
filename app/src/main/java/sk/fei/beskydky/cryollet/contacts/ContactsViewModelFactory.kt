package sk.fei.beskydky.cryollet.contacts

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository
import sk.fei.beskydky.cryollet.transactions.TransactionsViewModel

class ContactsViewModelFactory(private val contactsRepository: AppDatabaseDao) : ViewModelProvider.Factory {


    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(contactsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}