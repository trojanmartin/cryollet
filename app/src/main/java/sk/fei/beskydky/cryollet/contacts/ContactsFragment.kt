package sk.fei.beskydky.cryollet.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.database.repository.ContactsRepository
import sk.fei.beskydky.cryollet.databinding.ContactsFragmentBinding
import sk.fei.beskydky.cryollet.transactions.ContactsAdapter
import sk.fei.beskydky.cryollet.transactions.TransactionsAdapter


class ContactsFragment : Fragment() {

    private lateinit var viewModel: ContactsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding: ContactsFragmentBinding = DataBindingUtil.inflate(inflater,
                R.layout.contacts_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val databaseDataSource = AppDatabase.getInstance(application).appDatabaseDao
        val viewModelFactory = ContactsViewModelFactory(ContactsRepository(databaseDataSource))

        viewModel = ViewModelProvider(this, viewModelFactory)[ContactsViewModel::class.java]
        binding.viewModel = viewModel

        // Get a reference to the ViewModel associated with this fragment.
        val contactsViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ContactsViewModel::class.java)

        val adapter = ContactsAdapter()
        binding.contactsList.adapter = adapter
        binding.contactsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        binding.lifecycleOwner = this
        binding.viewModel = contactsViewModel
        return binding.root
    }
}