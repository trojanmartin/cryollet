package sk.fei.beskydky.cryollet.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sk.fei.beskydky.cryollet.models.User
import sk.fei.beskydky.cryollet.models.Wallet

@Dao
interface AppDatabaseDao {

    @Insert
    fun insertUser(user: User)

    @Insert
    fun insertWallet(wallet: Wallet)

    @Update
    fun updateUser(user: User)

    @Query("SELECT * from user WHERE userId = :key")
    fun getUserById(key: Long): User?

    @Query("DELETE FROM user")
    fun clearAllUsers()

    @Query("DELETE FROM wallet")
    fun clearAllWallets()

    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    fun getFirstUser(): User?

    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
    fun getFirstWallet(): Wallet?

}