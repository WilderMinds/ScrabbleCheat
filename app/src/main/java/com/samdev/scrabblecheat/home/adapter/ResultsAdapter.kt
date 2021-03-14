package com.samdev.scrabblecheat.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samdev.scrabblecheat.databinding.ItemLetterScoreBinding
import com.samdev.scrabblecheat.databinding.ItemResultBinding
import com.samdev.scrabblecheat.home.ItemClickListener
import com.samdev.scrabblecheat.model.WordResult

class ResultsAdapter(var horizontal: Boolean = false, private val clickListener: ItemClickListener) : ListAdapter<WordResult, ResultsAdapter.ItemViewHolder>(DiffCallback()) {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = if (horizontal) {
            ItemLetterScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
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

    inner class ItemViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordResult) = with(itemView) {
            if (horizontal) {
                (binding as ItemLetterScoreBinding).letter = item
            } else {
                (binding as ItemResultBinding).result = item
            }

            // pending binding
            binding.executePendingBindings()

            setOnClickListener {
                if (!horizontal) {
                    clickListener.onItemClicked(item)
                }
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