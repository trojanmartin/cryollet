package sk.fei.beskydky.cryollet.database.repository

import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import androidx.annotation.WorkerThread
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.sha256
import java.security.MessageDigest

class UserRepository (private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user:User) {
        appDatabaseDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun haveActiveAccount():Boolean {
        val user = get()
        return user.pin != null
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun isPinCorrect(pin:String):Boolean {
        val user = get()
        return user.pin == pin.sha256()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get():User {
       return appDatabaseDao.getUser()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createAndInsert(){
        appDatabaseDao.insertUser(User(pin = null))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePin(pin:String){
        val user = appDatabaseDao.getUser()
        val hashedPin = pin.sha256()
        user.pin = hashedPin
        appDatabaseDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllUsers()
    }



}