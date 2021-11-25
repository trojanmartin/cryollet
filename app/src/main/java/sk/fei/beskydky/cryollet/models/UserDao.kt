package sk.fei.beskydky.cryollet.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * from user_info WHERE userId = :key")
    fun getById(key: Long): User?

    @Query("DELETE FROM user_info")
    fun clear()

    @Query("SELECT * FROM user_info ORDER BY userId DESC LIMIT 1")
    fun getFirstUser(): User?

}