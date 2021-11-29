package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository
import kotlin.collections.ArrayList

class TransactionsViewModel(private val transRepo: TransactionRepository) : ViewModel() {
    private var _transactions = MutableLiveData<List<Transaction>>()
    val transactions: LiveData<List<Transaction>>
        get() = _transactions

     init {
         viewModelScope.launch {
             _transactions.value = transRepo.getAllTransactions()
         }
     }
}