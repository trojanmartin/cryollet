package sk.fei.beskydky.cryollet.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.fei.beskydky.cryollet.R
import sk.fei.beskydky.cryollet.data.model.Transaction
import sk.fei.beskydky.cryollet.data.model.TransactionWithContact
import sk.fei.beskydky.cryollet.databinding.TransactionItemViewBinding

private val ITEM_VIEW__TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

sealed class DataItem {
    abstract val id: Long
    data class TransactionItem(val transaction: TransactionWithContact): DataItem()      {
        override val id = transaction.transaction.transactionId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }
}

class TransactionsAdapter : ListAdapter<DataItem, RecyclerView.ViewHolder>(TransactionsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            ITEM_VIEW__TYPE_HEADER -> HeaderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder -> {
                val trans = getItem(position) as DataItem.TransactionItem
                holder.bind(trans.transaction.transaction)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW__TYPE_HEADER
            is DataItem.TransactionItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun addHeaderAndSubmitList(list: MutableList<TransactionWithContact>) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.TransactionItem(it) }
        }
        submitList(items)
    }

    class ViewHolder private constructor(private val binding: TransactionItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                    LayoutInflater.from(parent.context)

                val binding = TransactionItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Transaction){
            binding.transaction = item
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.transaction_header, parent, false)
                return HeaderViewHolder(view)
            }
        }
    }
}

class TransactionsDiffCallback : DiffUtil.ItemCallback<DataItem>(){
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}