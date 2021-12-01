package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import java.lang.Exception

class ContactsRepository private constructor(private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertIgnore(contact: Contact) {
        appDatabaseDao.insertContactIgnore(contact)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertReplace(contact: Contact) {
        appDatabaseDao.insertContactReplace(contact)
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

    companion object {

        @Volatile
        private var INSTANCE: ContactsRepository? = null

        fun getInstance(appDatabaseDao: AppDatabaseDao): ContactsRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = ContactsRepository(appDatabaseDao)
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}