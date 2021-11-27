package sk.fei.beskydky.cryollet.database.appDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import sk.fei.beskydky.cryollet.data.model.User
import sk.fei.beskydky.cryollet.data.model.Wallet

@Dao
interface AppDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

//    @Insert
//    fun insertWallet(wallet: Wallet)
//
//    @Update
//    fun updateUser(user: User)
//
//    @Query("SELECT * from user WHERE userId = :key")
//    fun getUserById(key: Long): LiveData<User>
//
//    @Query("DELETE FROM user")
//    fun clearAllUsers()
//
//    @Query("DELETE FROM wallet")
//    fun clearAllWallets()
//
//    @Query("SELECT * FROM user ORDER BY userId DESC LIMIT 1")
//    fun getFirstUser(): LiveData<User>
//
//    @Query("SELECT * FROM wallet ORDER BY walletId DESC LIMIT 1")
//    fun getFirstWallet(): LiveData<Wallet>

}