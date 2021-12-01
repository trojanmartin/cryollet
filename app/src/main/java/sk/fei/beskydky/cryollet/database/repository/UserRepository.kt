package sk.fei.beskydky.cryollet.database.repository

import android.content.Context
import android.util.Log
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.BuildConfig
import sk.fei.beskydky.cryollet.aesDecrypt
import sk.fei.beskydky.cryollet.aesEncrypt
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import java.security.MessageDigest

class UserRepository private constructor (private val appDatabaseDao: AppDatabaseDao) {

    var user :User? = null


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(user:User) {
        appDatabaseDao.updateUser(user)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun haveActiveAccount():Boolean {
        val user = get()
        return user != null
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun isPinCorrect(pin:String):Boolean {
        val user = get()
        val hashedPin = pin.aesEncrypt(BuildConfig.SECRET_KEY)
        return user?.pin == hashedPin
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(): User? {
        if(user != null){
            return user
        }else{
            Log.i("test", "volal sa")
             val a = appDatabaseDao.getUser()
            user = a
            return user
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createAndInsert(pin:String){
        appDatabaseDao.clearAllUsers()
        val hashedPin = pin.aesEncrypt(BuildConfig.SECRET_KEY)
        appDatabaseDao.insertUser(User(pin = hashedPin))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePin(pin:String){
        val user = appDatabaseDao.getUser()
        val hashedPin = pin.aesEncrypt(BuildConfig.SECRET_KEY)
        user?.pin = hashedPin
        if (user != null) {
            appDatabaseDao.updateUser(user)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPin() :String?{
        val user = appDatabaseDao.getUser()
        return user?.pin?.aesDecrypt(BuildConfig.SECRET_KEY)
    }



    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllUsers()
    }


    companion object {

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(appDatabaseDao: AppDatabaseDao): UserRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = UserRepository(appDatabaseDao)
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}