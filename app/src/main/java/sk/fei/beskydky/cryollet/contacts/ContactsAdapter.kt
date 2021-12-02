package sk.fei.beskydky.cryollet.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.util.CollectionUtils.isEmpty
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.data.model.Contact
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact
import sk.fei.beskydky.cryollet.databinding.ContactsItemBinding
import sk.fei.beskydky.cryollet.databinding.TransactionItemViewBinding

private val ITEM_VIEW__TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

sealed class ContactDataItem {
    abstract val name: String

    data class ContactItem(val contact: Contact) : ContactDataItem() {
        override val name = contact.name
    }

    object Header : ContactDataItem() {
        override val name = ""
    }
}

class ContactsAdapter : ListAdapter<ContactDataItem, RecyclerView.ViewHolder>(ContactsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW__TYPE_HEADER -> HeaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val contact = getItem(position) as ContactDataItem.ContactItem
                holder.bind(contact.contact)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ContactDataItem.Header -> ITEM_VIEW__TYPE_HEADER
            is ContactDataItem.ContactItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: MutableList<Contact>?) {
        var data = list
        if(data?.isEmpty() ?: true){
            data = null
        }

        val items = when (data) {
            null -> listOf(ContactDataItem.Header)
            else -> listOf(ContactDataItem.Header) + data.map { ContactDataItem.ContactItem(it) }
        }
        submitList(items)
    }

    class ViewHolder private constructor(private val binding: ContactsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                        LayoutInflater.from(parent.context)

                val binding = ContactsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Contact) {
            binding.contact = item
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.contacts_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }
}

class ContactsDiffCallback : DiffUtil.ItemCallback<ContactDataItem>() {
    override fun areItemsTheSame(oldItem: ContactDataItem, newItem: ContactDataItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: ContactDataItem, newItem: ContactDataItem): Boolean {
        return oldItem == newItem
    }
}
