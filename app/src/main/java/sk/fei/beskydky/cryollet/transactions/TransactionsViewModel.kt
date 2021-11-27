package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

data class FakeTransaction(val id: Long,
                           val account: String,
                           val name: String?,
                           val amount: Double,
                           val currency: String)

class TransactionsViewModel : ViewModel() {
    var cnt = 5L
    private val _trans = ArrayList<FakeTransaction>()
    val transactions = MutableLiveData<List<FakeTransaction>>()

     init {
         addTransaction()
         addTransaction()
         addTransaction()
         addTransaction()
     }

    fun addTransaction(){
        _trans.add(FakeTransaction(cnt,"GD42RQNXTRIW6YR3E2HXV5T2AI27L",null,cnt * 4.2, "Dolar tokens"))
        _trans.add(FakeTransaction(cnt,"GD42RQNXTRIW6YR3E2HXV5T2AI27LBRHOERV2JIYNFMXOBA234SWLQQB","Maros Kovalak${cnt}",cnt * 2.4, "Euro tokens"))
        _trans.add(FakeTransaction(cnt,"GD42RQNXTRIW6YR3E2HXV5T2AI27LBRHOERV2JIYNFMXOBA234SWLQQB","Jakub Trstensky${cnt}",cnt * 4.326, "Pessos tokens"))
        transactions.value = _trans
        cnt++
    }
}