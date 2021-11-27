package sk.fei.beskydky.cryollet.database.repository

import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import sk.fei.beskydky.cryollet.data.model.User

class UserRepository (private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        appDatabaseDao.insert(user)
    }

//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun get():LiveData<User> {
//        return  appDatabaseDao.getFirstUser()
//    }

}