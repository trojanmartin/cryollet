package sk.fei.beskydky.cryollet.database.appDatabase

import androidx.lifecycle.LiveData
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
    fun getUserById(key: Long): LiveData<User>

    @Query("DELETE FROM user")
    suspend fun clearAllUsers()

    @Query("DELETE FROM wallet")
    suspend fun clearAllWallets()

    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
    fun getFirstUser(): LiveData<User>

    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
    fun getFirstWallet(): LiveData<User>

}