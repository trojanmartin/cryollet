package sk.fei.beskydky.cryollet.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "pin")
    var pin: String,

    @ColumnInfo(name = "balance")
    var balance: Double,

    @ColumnInfo(name = "walletId")
    var walletId: String,

    @ColumnInfo(name = "firstName")
    var firstName: String,

    @ColumnInfo(name = "sureName")
    var sureName: String,


)