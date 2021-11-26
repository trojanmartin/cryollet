package sk.fei.beskydky.cryollet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "wallet")
data class Wallet (
    @PrimaryKey(autoGenerate = true)
    var walletId: Long = 0L,

    @ColumnInfo(name = "account_id")
    var account_id: String,

    @ColumnInfo(name = "balance")
    var balance: Double

)