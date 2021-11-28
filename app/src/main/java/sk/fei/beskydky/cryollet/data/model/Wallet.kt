package sk.fei.beskydky.cryollet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Wallet (
    @PrimaryKey(autoGenerate = true)
    var walletId: Long = 0L,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "account_id")
    var accountId: String,

    @ColumnInfo(name = "secret_key")
    var secretKey: String,

    @ColumnInfo(name = "balance")
    var balance: Double

)