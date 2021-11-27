package sk.fei.beskydky.cryollet.transactions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.fei.beskydky.cryollet.databinding.TransactionItemViewBinding

class TransactionsAdapter : ListAdapter<FakeTransaction, TransactionsAdapter.ViewHolder>(TransactionsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bind(getItem(position))
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

        fun bind(item: FakeTransaction){
            binding.transaction = item
            binding.executePendingBindings()
        }
    }
}

class TransactionsDiffCallback : DiffUtil.ItemCallback<FakeTransaction>(){
    override fun areItemsTheSame(oldItem: FakeTransaction, newItem: FakeTransaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FakeTransaction, newItem: FakeTransaction): Boolean {
        return oldItem == newItem
    }
}