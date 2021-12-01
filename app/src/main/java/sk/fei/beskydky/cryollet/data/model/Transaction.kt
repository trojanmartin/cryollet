package sk.fei.beskydky.cryollet.data.model
import androidx.room.*
import java.util.*

@Entity(tableName = "transactions",foreignKeys = arrayOf(
        ForeignKey(
                entity = Contact::class,
                parentColumns = arrayOf("wallet_id"),
                childColumns = arrayOf("external_walletId"),
                onDelete = ForeignKey.CASCADE
        )))
data class Transaction (
    @PrimaryKey()
    var transactionId: String,

    @ColumnInfo(name = "external_walletId")
    var externalWalletId: String,

    @ColumnInfo(name = "isReceivedType")
    var isReceivedType: Boolean,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "currency")
    var currency: String,

    @ColumnInfo(name = "amount")
    var amount: String

)