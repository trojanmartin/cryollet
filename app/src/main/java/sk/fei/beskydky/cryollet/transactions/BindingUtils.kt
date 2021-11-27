package sk.fei.beskydky.cryollet.transactions

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("amountFormatted")
fun TextView.setAmountString(item: FakeTransaction) {
    text = item.amount.toString()
}


@BindingAdapter("accountString")
fun TextView.setAccountString(item: FakeTransaction) {
    text = item.account
}