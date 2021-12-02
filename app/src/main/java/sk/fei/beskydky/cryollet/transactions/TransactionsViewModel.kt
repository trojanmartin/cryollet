package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository

class TransactionsViewModel(private val database: AppDatabaseDao,
                            private val transactionRepository: TransactionRepository,
                            private val balanceRepository: BalanceRepository) : ViewModel() {
    val transactions =  database.getAllTransactionsWithContactLiveData()


    fun refresh(){
        viewModelScope.launch {
            transactionRepository.refreshDatabaseFromStellar()
            balanceRepository.refreshBalances()
        }
    }
}