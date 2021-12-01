package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import org.stellar.sdk.KeyPair
import sk.fei.beskydky.cryollet.BuildConfig
import sk.fei.beskydky.cryollet.aesDecrypt
import sk.fei.beskydky.cryollet.aesEncrypt
import sk.fei.beskydky.cryollet.data.model.Wallet
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler

class WalletRepository(private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {

    private val userRepository = UserRepository(appDatabaseDao)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(wallet: Wallet) {
        appDatabaseDao.updateWallet(wallet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun get(): Wallet? {
        return appDatabaseDao.getWallet()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createFromSecretAndInsert(secret:String,userId:Long, pin:String): Boolean {
        var res = false
        val account = stellarDataSource.getAccount(secret)
        if(account != null){
            res = true
            appDatabaseDao.clearAllWallets()
            val secretKey = account.secretSeed.joinToString("")
            val hashedSecretKey = secretKey.aesEncrypt(pin)
            appDatabaseDao.insertWallet(Wallet(userId = userId, accountId = account.accountId, secretKey = hashedSecretKey))
        }
        return res
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createNewAndInsert(userId:Long, pin:String) {
        appDatabaseDao.clearAllWallets()
        var registeredKeyPair = stellarDataSource.createAccount()
        val secretKey = registeredKeyPair.secretSeed.joinToString("")
        val hashedSecretKey = secretKey.aesEncrypt(pin)
        appDatabaseDao.insertWallet(Wallet(userId = userId, accountId = registeredKeyPair.accountId, secretKey = hashedSecretKey))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSecretKey(pin:String) :String {
        val wallet = get()
        var key = "a"
        if (wallet != null){
            key = wallet.secretKey.aesDecrypt(pin)
        }

        return key
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        appDatabaseDao.clearAllWallets()
    }


}