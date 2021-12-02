package sk.fei.beskydky.cryollet.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import sk.fei.beskydky.cryollet.data.model.Balance

@BindingAdapter("balance")
fun TextView.setAccountName(item: Balance) {
    text =  String.format("%.2f", item.amount.toFloat())
}

@BindingAdapter("assetName")
fun TextView.setWalletId(item: Balance) {
    text = item.assetName
}