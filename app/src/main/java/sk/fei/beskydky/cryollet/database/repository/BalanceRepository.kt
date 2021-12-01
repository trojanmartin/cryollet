package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import org.stellar.sdk.AssetTypeNative
import org.stellar.sdk.KeyPair
import org.stellar.sdk.responses.operations.PaymentOperationResponse
import sk.fei.beskydky.cryollet.data.model.Balance
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import java.lang.Exception

class BalanceRepository (private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {

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
                    Balance(balanceId = 0L, assetName = "native", issuer = "first", amount = "0"))
            appDatabaseDao.insertBalance(list)
        }
        refreshBalances()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun refreshBalances() {
        val storage = appDatabaseDao.getAllBalances()

        val walletRepository = WalletRepository(appDatabaseDao, stellarDataSource)
        val userRepository = UserRepository(appDatabaseDao)
        val source: KeyPair = KeyPair.fromSecretSeed(walletRepository.getSecretKey(userRepository.getPin()!!))
        val balyAnces = stellarDataSource.getBalances(source)!!

        for (i in 1 until balyAnces.size) {
            val item = balyAnces[i]
            for (s in storage){
                if(s.assetName == item.assetType){
                    s.amount = item.balance
                }
            }
        }
        appDatabaseDao.updateBalance(storage)
    }




}