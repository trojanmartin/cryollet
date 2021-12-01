package sk.fei.beskydky.cryollet.home.sendpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.stellar.sdk.responses.AccountResponse
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabaseDao
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository

class SendPaymentViewModelFactory(private val contactsRepository: BalanceRepository,
                                  private val database: AppDatabaseDao) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SendPaymentViewModel::class.java)) {
            return SendPaymentViewModel(contactsRepository, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}