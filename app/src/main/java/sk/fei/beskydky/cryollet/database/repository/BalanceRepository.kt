package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import org.stellar.sdk.AssetTypeNative
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.data.model.Balance
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import java.lang.Exception

class BalanceRepository (private val appDatabaseDao: AppDatabaseDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(balance: Balance){
            appDatabaseDao.insertBalance(balance)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(balances: MutableList<Balance>){
        appDatabaseDao.insertBalance(balances)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(balance: Balance){
        appDatabaseDao.updateBalance(balance)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getById(id: Long): Balance? {
        return appDatabaseDao.getBalanceById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(): MutableList<Balance> {
        return appDatabaseDao.getAllBalances()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getByShortName(shortName: String): Balance? {
        return appDatabaseDao.getBalanceByShortName(shortName)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun fillWithData(){
        val storage = appDatabaseDao.getAllBalances()
        if(storage.size == 0){
            var list = listOf(
                    Balance(balanceId = 0L, assetName = "first", issuer = "first", amount = "0"))
            appDatabaseDao.insertBalance(list)
        }
    }

}