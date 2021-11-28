package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import sk.fei.beskydky.cryollet.data.model.Wallet
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import javax.sql.DataSource

class WalletRepository(private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {


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
    suspend fun createAndInsert(userId:Long) {
        var registeredKeyPair = stellarDataSource.createAccount()
        appDatabaseDao.insertWallet(Wallet(user_id = userId, account_id = registeredKeyPair.accountId, secret_key = registeredKeyPair.secretSeed.toString(), balance = 10000.0))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllWallets()
    }


}