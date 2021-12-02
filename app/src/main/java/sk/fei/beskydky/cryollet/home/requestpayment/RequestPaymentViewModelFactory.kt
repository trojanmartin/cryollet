package sk.fei.beskydky.cryollet.home.requestpayment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.database.repository.BalanceRepository
import sk.fei.beskydky.cryollet.database.repository.WalletRepository
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeViewModel

class RequestPaymentViewModelFactory(private val walletRepository: WalletRepository,
                                     private val balanceRepository: BalanceRepository) :
        ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestPaymentViewModel::class.java)) {
            return RequestPaymentViewModel(walletRepository, balanceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}