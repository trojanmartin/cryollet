package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository

class TransactionsViewModel(private val transRepo: TransactionRepository) : ViewModel() {
    private var _transactions = MutableLiveData<MutableList<TransactionWithContact>>()
    val transactions: LiveData<MutableList<TransactionWithContact>>
        get() = _transactions




    init {
        viewModelScope.launch {
            //transRepo.makeTransaction("GA3KRKTFY5XFKNA6TGDJYRLDIGVJ4XUDOB3E6ZWOHS46HTSQUNTF544D", "10", "test")
            _transactions.value = transRepo.getAllTransactions(true)
        }
    }



}