package sk.fei.beskydky.cryollet.database.repository

import android.content.res.Resources
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.stellar.sdk.AssetTypeCreditAlphaNum4
import org.stellar.sdk.AssetTypeNative
import org.stellar.sdk.KeyPair
import org.stellar.sdk.responses.operations.PaymentOperationResponse
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.stellar.StellarHandler

class TransactionRepository(private val appDatabaseDao: AppDatabaseDao, private val stellarDataSource: StellarHandler) {

    val walletRepository = WalletRepository(appDatabaseDao, stellarDataSource)
    val userRepository = UserRepository(appDatabaseDao)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun makeTransaction(
            destinationId: String = "GAWB5RG6F4X3SUBXYI3O3M4ZED6KFHMORIM5URKZI5BYRCJHGOO5XSLP",
            amount:String,
            memo:String = "") {
        val source: KeyPair = KeyPair.fromSecretSeed(walletRepository.getSecretKey(userRepository.getPin()!!))
       stellarDataSource.sendTransaction(source, destinationId, "XLM",amount, memo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllTransactions(refreshFromStellar:Boolean = false): MutableList<TransactionWithContact> {
        if(refreshFromStellar){
            refreshDatabaseFromStellar()
        }
        return appDatabaseDao.getAllTransactions()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getSentTransactions(refreshFromStellar:Boolean = false): MutableList<TransactionWithContact>{
        if(refreshFromStellar){
            refreshDatabaseFromStellar()
        }
        return appDatabaseDao.getSentTransactions()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getReceivedTransactions(refreshFromStellar:Boolean = false): MutableList<TransactionWithContact> {
        if(refreshFromStellar){
            refreshDatabaseFromStellar()
        }

        return appDatabaseDao.getReceivedTransactions()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend private fun refreshDatabaseFromStellar(){
        val listTransactions = getAllTransactionsFromStellar()
        val listContacts = mutableListOf<Contact>()
        for (transaction in listTransactions){
            listContacts.add(Contact(transaction.externalWalletId, transaction.externalWalletId))
        }
        appDatabaseDao.insertContactIgnore(listContacts)
        appDatabaseDao.insertTransactions(listTransactions)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun loginWithSecretKey(secretKey: String) {
        //TODO MAROS
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    private suspend fun getAllTransactionsFromStellar(): MutableList<Transaction> {
        val result = mutableListOf<Transaction>()
        val pin = userRepository.getPin()!!
        val sk = walletRepository.getSecretKey(pin)
        val source: KeyPair = KeyPair.fromSecretSeed(sk)
        val response = stellarDataSource.getPayments(source)
        if (response != null) {
            for (i in 1 until response.size){
                val item = response[i] as PaymentOperationResponse

                val isReceivedPayment = source.accountId != item.sourceAccount

                var code = when(item.asset){
                    is AssetTypeCreditAlphaNum4 -> (item.asset as AssetTypeCreditAlphaNum4).code
                    is AssetTypeNative -> "XLM"
                    else -> throw Resources.NotFoundException("Asset not found")
                }

                result.add(
                        Transaction(
                                transactionId = item.id.toString(),
                                externalWalletId = if(isReceivedPayment) item.from else item.to,
                                date = item.createdAt,
                                currency = code,
                                amount = item.amount,
                                isReceivedType = isReceivedPayment
                        ))
            }
        }
        return result
    }


}