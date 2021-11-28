package sk.fei.beskydky.cryollet.database.appDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.data.model.Wallet

@Dao
interface AppDatabaseDao {


    //USER
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * from user WHERE userId = :key")
    suspend fun getUserById(key: Long): User

    @Query("DELETE FROM user")
    suspend fun clearAllUsers()

    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    suspend fun getFirstUser(): User


    //WALLET
    @Insert
    suspend fun insertWallet(wallet: Wallet)

    @Update
    suspend fun updateWallet(wallet: Wallet)

    @Query("SELECT * from wallet WHERE walletId = :key")
    suspend fun getWalletById(key: Long): Wallet

    @Query("DELETE FROM wallet")
    suspend fun clearAllWallets()

    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
    suspend fun getFirstWallet(): Wallet



}