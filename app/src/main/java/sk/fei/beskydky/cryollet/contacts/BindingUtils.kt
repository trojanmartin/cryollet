package sk.fei.beskydky.cryollet.contacts

import android.widget.TextView
import androidx.databinding.BindingAdapter
import sk.fei.beskydky.cryollet.data.model.Contact

@BindingAdapter("accountName")
fun TextView.setAccountName(item: Contact) {
    text = item.name
}

@BindingAdapter("walledId")
fun TextView.setWalletId(item: Contact) {
    text = item.walletId
}