package sk.fei.beskydky.cryollet

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import sk.fei.beskydky.cryollet.database.stellar.StellarHandler
import sk.fei.beskydky.cryollet.models.User
import org.stellar.sdk.KeyPair

class MenuViewModel : ViewModel() {

    private val _eventTransactionsClicked = MutableLiveData<Boolean>()
    val eventTransactionClicked: LiveData<Boolean?>
        get() = _eventTransactionsClicked

    private val _eventHomeClicked = MutableLiveData<Boolean>()
    val eventHomeClicked: LiveData<Boolean?>
        get() = _eventHomeClicked

    private val _eventContactsClicked = MutableLiveData<Boolean>()
    val eventContactsClicked: LiveData<Boolean?>
        get() = _eventContactsClicked

    fun onHomeClicked(){
        _eventHomeClicked.value = true
    }

    fun onContactsClicked(){
        _eventContactsClicked.value = true
    }

    fun onTransactionsClicked(){
        _eventTransactionsClicked.value = true
    }




}