package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import sk.fei.beskydky.cryollet.data.model.Wallet
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao

class WalletRepository(private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(wallet: Wallet) {
        appDatabaseDao.insertWallet(wallet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(wallet: Wallet) {
        appDatabaseDao.updateWallet(wallet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(): Wallet {
        return appDatabaseDao.getFirstWallet()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun create(userId:Long):Wallet {
        //STELAR
       return Wallet(user_id = userId, account_id = "STELAR", secret_key = "STELAR", balance = 10000.0)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllWallets()
    }


}