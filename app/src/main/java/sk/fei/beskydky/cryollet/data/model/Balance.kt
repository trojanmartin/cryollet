package sk.fei.beskydky.cryollet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class Balance (

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var balanceId: Long,

        @ColumnInfo(name = "asset_name")
        var assetName: String,

        @ColumnInfo(name = "issuer")
        var issuer: String,

        @ColumnInfo(name = "amount")
        var amount: String,
)