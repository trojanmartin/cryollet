package sk.fei.beskydky.cryollet.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sk.fei.beskydky.cryollet.data.model.Balance
import sk.fei.beskydky.cryollet.databinding.BalanceItemBinding


class HomeAdapter : ListAdapter<Balance, HomeAdapter.ViewHolder>(BalanceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val balance = getItem(position)
        holder.bind(balance)
    }

    fun addHeaderAndSubmitList(list: MutableList<Balance>?) {
        val data = list?.map { it }
        submitList(data)
    }


    class ViewHolder private constructor(private val binding: BalanceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                        LayoutInflater.from(parent.context)

                val binding = BalanceItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: Balance) {
            binding.balance = item
            binding.executePendingBindings()
        }
    }
}

class BalanceDiffCallback : DiffUtil.ItemCallback<Balance>() {
    override fun areItemsTheSame(oldItem: Balance, newItem: Balance): Boolean {
        return oldItem.balanceId == newItem.balanceId
    }

    override fun areContentsTheSame(oldItem: Balance, newItem: Balance): Boolean {
        return oldItem == newItem
    }
}
