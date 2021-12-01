package sk.fei.beskydky.cryollet.transactions

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact

@BindingAdapter("amountFormatted")
fun TextView.setAmountString(item: TransactionWithContact) {
    text =  String.format("%.2f", item.transaction.amount.toFloat())

    if(item.transaction.isReceivedType) {
        setTextColor(ContextCompat.getColor(context, R.color.credit))
    }else{
        setTextColor(ContextCompat.getColor(context, R.color.debet))
    }

}


@BindingAdapter("accountString")
fun TextView.setAccountString(item: TransactionWithContact) {
        text = item.contact.name.take(20) + "..."
}

@BindingAdapter("currencyString")
fun TextView.setCurrencyString(item: TransactionWithContact) {
    text = item.transaction.currency
}