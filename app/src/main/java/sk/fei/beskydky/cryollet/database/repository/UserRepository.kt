package sk.fei.beskydky.cryollet.database.repository

import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import sk.fei.beskydky.cryollet.data.model.User

class UserRepository (private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user:User) {
        appDatabaseDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun haveActiveAccount():Boolean {
        val u = get()
        return u != null && u.pin != "-1"
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun isPinCorrect(pin:String):Boolean {
        val u = get()
        return u.pin == pin
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get():User {
       return appDatabaseDao.getFirstUser()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createAndInsert(){
        appDatabaseDao.insertUser(User(pin = (100..999).random().toString()))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllUsers()
    }

}