package sk.fei.beskydky.cryollet.database

import com.android.volley.RequestQueue
import sk.fei.beskydky.cryollet.models.User
import sk.fei.beskydky.cryollet.models.Wallet

class MainRepository(private val appDatabaseDao: AppDatabaseDao) {

    suspend fun insertUser(user: User) =
        appDatabaseDao.insertUser((user))

    suspend fun insertWallet(wallet: Wallet) =
        appDatabaseDao.insertWallet((wallet))

    suspend fun getUser() =
        appDatabaseDao.getFirstUser()

    suspend fun getWalllet() =
        appDatabaseDao.getFirstWallet()
}