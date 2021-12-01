package sk.fei.beskydky.cryollet.database.appDatabase


import androidx.room.*
import sk.fei.beskydky.cryollet.data.model.*
import sk.fei.beskydky.cryollet.data.model.Transaction

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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transaction)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransactions(transactions: MutableList<Transaction>)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions")
    suspend fun clearAllTransactions()


    @Query("SELECT * FROM transactions" )
    suspend fun getAllTransactions(): MutableList<TransactionWithContact>

    @Query("SELECT * FROM transactions WHERE isReceivedType = 1 ORDER BY date DESC")
    suspend fun getReceivedTransactions(): MutableList<TransactionWithContact>

    @Query("SELECT * FROM transactions WHERE isReceivedType = 0 ORDER BY date DESC")
    suspend fun getSentTransactions(): MutableList<TransactionWithContact>

    //CONTACTS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contacts: MutableList<Contact>)

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("SELECT * from contacts")
    suspend fun getAllContacts(): MutableList<Contact>

    @Query("SELECT * from contacts WHERE name = :name")
    suspend fun getContactByName(name: String): Contact?

    @Query("SELECT * from contacts WHERE wallet_id = :walletId")
    suspend fun getContactByWalletId(walletId: String): Contact?

    @Query("DELETE FROM contacts")
    suspend fun clearAllContacts()

}