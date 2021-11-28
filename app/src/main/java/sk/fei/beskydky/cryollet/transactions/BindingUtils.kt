package sk.fei.beskydky.cryollet.transactions

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("amountFormatted")
fun TextView.setAmountString(item: FakeTransaction) {
    text = String.format("%.2f", item.amount)
}


@BindingAdapter("accountString")
fun TextView.setAccountString(item: FakeTransaction) {
    text = item.name ?: item.account
}

@BindingAdapter("currencyString")
fun TextView.setCurrencyString(item: FakeTransaction) {
    text = item.currency
}