package sk.fei.beskydky.cryollet.transactions

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import sk.fei.beskydky.cryollet.data.model.Transaction

@BindingAdapter("amountFormatted")
fun TextView.setAmountString(item: Transaction) {
    text = item.amount
}


@BindingAdapter("accountString")
fun TextView.setAccountString(item: Transaction) {
    text = item.externalWalletId// ?: item.account
}

@BindingAdapter("currencyString")
fun TextView.setCurrencyString(item: Transaction) {
    text = item.currency
}