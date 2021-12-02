package sk.fei.beskydky.cryollet.database.appDatabase


import androidx.lifecycle.LiveData
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

    @Query("SELECT * FROM transactions" )
    fun getAllTransactionsLiveData(): LiveData<MutableList<Transaction>>

    @Query("SELECT * FROM transactions" )
    fun getAllTransactionsWithContactLiveData(): LiveData<MutableList<TransactionWithContact>>

    @Query("SELECT * FROM transactions WHERE isReceivedType = 1 ORDER BY date DESC")
    suspend fun getReceivedTransactions(): MutableList<TransactionWithContact>

    @Query("SELECT * FROM transactions WHERE isReceivedType = 0 ORDER BY date DESC")
    suspend fun getSentTransactions(): MutableList<TransactionWithContact>


    //CONTACTS
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContactIgnore(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContactIgnore(contacts: MutableList<Contact>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactReplace(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContactReplace(contacts: MutableList<Contact>)

    @Query("SELECT * from contacts where name != wallet_id ORDER BY name ASC")
    suspend fun getAllContacts(): MutableList<Contact>

    @Query("SELECT * FROM contacts where name != wallet_id ORDER BY name ASC")
    fun getAllContactsLiveData(): LiveData<MutableList<Contact>>

    @Query("SELECT * from contacts WHERE name = :name")
    suspend fun getContactByName(name: String): Contact?

    @Query("SELECT * from contacts WHERE wallet_id = :walletId")
    suspend fun getContactByWalletId(walletId: String): Contact?

    @Query("DELETE FROM contacts")
    suspend fun clearAllContacts()

    //BALANCES
    @Insert
    suspend fun insertBalance(balance: Balance)

    @Insert
    suspend fun insertBalance(balances: List<Balance>)

    @Update
    suspend fun updateBalance(balance: Balance)

    @Update
    suspend fun updateBalance(balance: List<Balance>)

    @Query("SELECT * from balances")
    suspend fun getAllBalances(): MutableList<Balance>

    @Query("SELECT * from balances")
    fun getAllBalancesLiveData(): LiveData<MutableList<Balance>>

    @Query("DELETE FROM balances")
    suspend fun clearAllBalances()

    @Query("SELECT * FROM balances WHERE id = :id")
    suspend fun getBalanceById(id:Long): Balance?

    @Query("SELECT * FROM balances WHERE asset_name = :assetName")
    suspend fun getBalanceByShortName(assetName:String): Balance?

}