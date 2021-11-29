package sk.fei.beskydky.cryollet.data.model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transactions")
data class Transaction (
    @PrimaryKey(autoGenerate = true)
    var transactionId: Long = 0L,

    @ColumnInfo(name = "origin_wallet")
    var originWallet: String,

    @ColumnInfo(name = "destination_wallet")
    var destinationWallet: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "currency")
    var currency: String,

    @ColumnInfo(name = "amount")
    var amount: String
)