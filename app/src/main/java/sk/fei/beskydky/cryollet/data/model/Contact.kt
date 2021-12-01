package sk.fei.beskydky.cryollet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact (

    @PrimaryKey()
    @ColumnInfo(name = "wallet_id")
    var walletId: String,

    @ColumnInfo(name = "name")
    var name: String

)