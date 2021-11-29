package sk.fei.beskydky.cryollet.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository

class TransactionsViewModelFactory(private var repo: TransactionRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionsViewModel::class.java)) {
            return TransactionsViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}