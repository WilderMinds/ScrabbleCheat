package com.samdev.scrabblecheat.home.adapter

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.samdev.scrabblecheat.databinding.ItemResultBinding
import com.samdev.scrabblecheat.model.WordResult

class ResultsAdapter : ListAdapter<WordResult, ResultsAdapter.ItemViewHolder>(DiffCallback()) {

    private lateinit var binding: ItemResultBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ItemViewHolder(binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordResult) = with(itemView) {

            binding.result = item

            setOnClickListener {
                // TODO: Handle on click (Maybe View word definition)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<WordResult>() {
    override fun areItemsTheSame(oldItem: WordResult, newItem: WordResult): Boolean {
        return oldItem.word.equals(newItem.word, ignoreCase = true)
    }

    override fun areContentsTheSame(oldItem: WordResult, newItem: WordResult): Boolean {
        return oldItem == newItem
    }
}