package sk.fei.beskydky.cryollet.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.data.model.Wallet

@Dao
interface AppDatabaseDao {

    @Insert
    suspend fun insertUser(user: User)

    @Insert
    suspend fun insertWallet(wallet: Wallet)

    @Update
    suspend fun updateUser(user: User)

    @Query("SELECT * from user WHERE userId = :key")
    suspend fun getUserById(key: Long): User?

    @Query("DELETE FROM user")
    suspend fun clearAllUsers()

    @Query("DELETE FROM wallet")
    suspend fun clearAllWallets()

    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    suspend fun getFirstUser(): User?

    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
    suspend fun getFirstWallet(): Wallet?

}