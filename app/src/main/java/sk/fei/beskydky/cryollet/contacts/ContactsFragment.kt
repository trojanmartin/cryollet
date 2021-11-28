package sk.fei.beskydky.cryollet.contacts

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.database.appDatabase.AppDatabase
import sk.fei.beskydky.cryollet.databinding.ContactsFragmentBinding


class ContactsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ContactsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.contacts_fragment, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = AppDatabase.getInstance(application).appDatabaseDao
        val viewModelFactory = ContactsViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val contactsViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ContactsViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel =contactsViewModel
        return binding.root
    }
}