package sk.fei.beskydky.cryollet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "publicKey")
    var publicKey: String,

    @ColumnInfo(name = "secretKey")
    var secretKey: String,

    @ColumnInfo(name = "pin")
    var pin: String
)