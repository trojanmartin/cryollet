package sk.fei.beskydky.cryollet.database.repository

import androidx.annotation.WorkerThread
import org.stellar.sdk.AssetTypeNative
import org.stellar.sdk.KeyPair
import org.stellar.sdk.responses.operations.PaymentOperationResponse
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler

class TransactionRepository(private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {

    val walletRepository = WalletRepository(appDatabaseDao, stellarDataSource)
    val userRepository = UserRepository(appDatabaseDao)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun makeTransaction(destinationId: String = "GAWB5RG6F4X3SUBXYI3O3M4ZED6KFHMORIM5URKZI5BYRCJHGOO5XSLP") {
        val user = appDatabaseDao.getUser()
        val source: KeyPair = KeyPair.fromSecretSeed(walletRepository.getSecretKey(userRepository.getPin()!!))
       stellarDataSource.sendTransaction(source, destinationId, AssetTypeNative(),"1", "testTransaction")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllTransactions(): MutableList<Transaction> {
        val result = mutableListOf<Transaction>()
        val user = appDatabaseDao.getUser()
        val source: KeyPair = KeyPair.fromSecretSeed(walletRepository.getSecretKey(userRepository.getPin()!!))
        var response = stellarDataSource.getPayments(source)
        if (response != null) {
            for (i in  1 .. response.size){
                val item = response[i] as PaymentOperationResponse
                result.add(
                    Transaction(
                        originWallet = item.sourceAccount,
                        destinationWallet = item.to,
                        date = item.createdAt,
                        currency = item.asset.toString(),
                        amount = item.amount
                ))
            }
        }
        return result
    }
}