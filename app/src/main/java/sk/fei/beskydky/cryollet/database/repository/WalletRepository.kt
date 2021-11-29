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
//        val source: KeyPair = KeyPair.fromSecretSeed("SCZWUQZX7AD7OENXXKNOHJXSOT2WAJOBRLVV7YNASLAMOECWTJPAC3WS")
//        appDatabaseDao.clearAllWallets()
//        val wallet = appDatabaseDao.getUser()?.let { Wallet(userId = it.userId,accountId = source.accountId.toString(), secretKey = source.secretSeed.toString().aesEncrypt(BuildConfig.SECRET_KEY), balance = 10000.0 ) }
//        if (wallet != null) {
//            appDatabaseDao.insertWallet(wallet)
//        }
        return appDatabaseDao.getWallet()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun createAndInsert(userId:Long, pin:String) {
        appDatabaseDao.clearAllWallets()
        var registeredKeyPair = stellarDataSource.createAccount()
        val hashedSecretKey = registeredKeyPair.secretSeed.joinToString("")
        appDatabaseDao.insertWallet(Wallet(userId = userId, accountId = registeredKeyPair.accountId, secretKey = hashedSecretKey , balance = 10000.0))
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