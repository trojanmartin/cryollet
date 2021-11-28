package sk.fei.beskydky.cryollet

import android.view.View
import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import sk.fei.beskydky.cryollet.contacts.ContactsFragment
import sk.fei.beskydky.cryollet.home.HomeFragment
import sk.fei.beskydky.cryollet.home.qrcode.QrCodeFragment
import sk.fei.beskydky.cryollet.home.sendpayment.SendPaymentFragment
import sk.fei.beskydky.cryollet.transactions.TransactionsFragment
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeFragment

class BottomMenuAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){

            0 -> return TransactionsFragment()
            1 -> return HomeFragment()
            2 -> return ContactsFragment()
        }
        return HomeFragment()
    }
}