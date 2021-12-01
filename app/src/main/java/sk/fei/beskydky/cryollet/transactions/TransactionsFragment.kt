package sk.fei.beskydky.cryollet.transactions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.TransactionRepository
import sk.fei.beskydky.cryollet.database.repository.UserRepository
import sk.fei.beskydky.cryollet.databinding.TransactionListFragmentBinding
import sk.fei.beskydky.cryollet.stellar.StellarHandler
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeViewModel
import sk.fei.beskydky.cryollet.ui.login.pin.PinCodeViewModelFactory

class TransactionsFragment : Fragment() {
    private lateinit var viewModel: TransactionsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding: TransactionListFragmentBinding = DataBindingUtil.inflate(inflater,
           R.layout.transaction_list_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val stellarDataSource = StellarHandler.getInstance(application)
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val viewModelFactory = TransactionsViewModelFactory(TransactionRepository(databaseDataSource, stellarDataSource))

        viewModel = ViewModelProvider(this, viewModelFactory)[TransactionsViewModel::class.java]
        binding.viewModel = viewModel

        val adapter = TransactionsAdapter()
        binding.transactionList.adapter = adapter
        binding.transactionList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.transactions.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })



        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this
       return binding.root
    }
}