package sk.fei.beskydky.cryollet.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithContact(
        @Embedded val transaction: Transaction,
        @Relation(
                parentColumn = "external_walletId",
                entityColumn = "wallet_id"
        )
        val contact: Contact
)