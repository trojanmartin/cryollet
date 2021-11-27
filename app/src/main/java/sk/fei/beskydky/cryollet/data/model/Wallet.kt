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
    var user_id: Long,

    @ColumnInfo(name = "account_id")
    var account_id: String,

    @ColumnInfo(name = "secret_key")
    var secret_key: String,

    @ColumnInfo(name = "balance")
    var balance: Double

)