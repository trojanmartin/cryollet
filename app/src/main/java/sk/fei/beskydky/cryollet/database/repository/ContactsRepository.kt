package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao

class ContactsRepository (private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: Contact) {
        appDatabaseDao.insertContact(contact)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contacts: MutableList<Contact>) {
        appDatabaseDao.insertContacts(contacts)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get() : MutableList<Contact> {
       return  appDatabaseDao.getAllContacts()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getByName(name:String) : Contact? {
        return appDatabaseDao.getContactByName(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getByWaleId(walletId:String) : Contact? {
        return appDatabaseDao.getContactByWalletId(walletId)
    }

}