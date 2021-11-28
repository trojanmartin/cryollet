package sk.fei.beskydky.cryollet.database.appDatabase


import androidx.room.*
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.data.model.Wallet

@Dao
interface AppDatabaseDao {

    //USER
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user")
    suspend fun clearAllUsers()

    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    suspend fun getUser(): User?


    //WALLET
    @Insert
    suspend fun insertWallet(wallet: Wallet)

    @Update
    suspend fun updateWallet(wallet: Wallet)

    @Query("SELECT * from wallet WHERE user_id = :key")
    suspend fun getWalletByUserId(key: Long): Wallet?

    @Query("DELETE FROM wallet")
    suspend fun clearAllWallets()

    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
    suspend fun getWallet(): Wallet?


    //TRANSACTIONS
    //USER
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTransaction(transaction: Transaction)
//
//    @Update
//    suspend fun updateTransaction(transaction: Transaction)
//
//    @Query("DELETE FROM transactions")
//    suspend fun clearAllTransactions()
//
//    @Query("SELECT * FROM transactions ORDER BY date DESC")
//    suspend fun getAllTransactions(): List<Transaction>
//
//    @Query("SELECT * FROM transactions WHERE origin_wallet = :contactId")
//    suspend fun getTransactionsByContactId(contactId:Long): List<Transaction>

}