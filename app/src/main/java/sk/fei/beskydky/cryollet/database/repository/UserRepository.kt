package sk.fei.beskydky.cryollet.database.repository

import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import sk.fei.beskydky.cryollet.data.model.User

class UserRepository (private val appDatabase: AppDatabaseDao) {
    val user: LiveData<User> = appDatabase.getFirstUser()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        appDatabase.insertUser(user)
    }

}