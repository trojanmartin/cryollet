package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class FakeTransaction(val id: Int,
                           val account: String,
                           val amount: Int)

class TransactionsViewModel : ViewModel() {
    private val _trans = ArrayList<FakeTransaction>()
    val transactions = MutableLiveData<List<FakeTransaction>>()

    var cnt = 5

    fun addTransaction(){
        _trans.add(FakeTransaction(cnt,"sdasd${cnt}",cnt))
        transactions.value = _trans
        cnt++
    }
}