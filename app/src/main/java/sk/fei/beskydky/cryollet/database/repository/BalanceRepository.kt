package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.data.model.Balance
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import sk.fei.beskydky.cryollet.toNullable

class BalanceRepository private constructor(private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {

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
                    Balance(balanceId = 0L, assetName = "XLM", issuer = "SDA5JK3V55GXYBC74Z2QHYBC2DTRA4UN7C5APPKJWLUK3ETBGF5RKJBZ", amount = "0", assetDescription = "Lumen"),
                    Balance(balanceId = 0L, assetName = "BTC", issuer = "SDA5JK3V55GXYBC74Z2QHYBC2DTRA4UN7C5APPKJWLUK3ETBGF5RKJBZ", amount = "0", assetDescription = "Bitcoin"),
                    Balance(balanceId = 0L, assetName = "EUR", issuer = "SDA5JK3V55GXYBC74Z2QHYBC2DTRA4UN7C5APPKJWLUK3ETBGF5RKJBZ", amount = "0", assetDescription = "Euro"),
                    Balance(balanceId = 0L, assetName = "USD", issuer = "SDA5JK3V55GXYBC74Z2QHYBC2DTRA4UN7C5APPKJWLUK3ETBGF5RKJBZ", amount = "0", assetDescription = "Dollar"),
            )
            appDatabaseDao.insertBalance(list)
        }
        refreshBalances()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun refreshBalances() {
        val storage = appDatabaseDao.getAllBalances()

        val walletRepository = WalletRepository.getInstance(appDatabaseDao, stellarDataSource)
        val userRepository = UserRepository.getInstance(appDatabaseDao)
        val source: KeyPair = KeyPair.fromSecretSeed(walletRepository.getSecretKey(userRepository.getPin()!!))
        val balyAnces = stellarDataSource.getBalances(source)!!

        for (element in balyAnces) {

            var e = storage.firstOrNull { x -> x.assetName == element.assetCode.toNullable()  }
            if(e != null){
                e.amount = element.balance
            }else if(element.assetType == "native"){
                e = storage.first { x -> x.assetName == "XLM" }
                e.amount = element.balance
            }
        }
        appDatabaseDao.updateBalance(storage)
    }


    companion object {

        @Volatile
        private var INSTANCE: BalanceRepository? = null

        fun getInstance(appDatabaseDao: AppDatabaseDao, stellarDataSource: StellarHandler): BalanceRepository {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = BalanceRepository(appDatabaseDao,stellarDataSource)
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}