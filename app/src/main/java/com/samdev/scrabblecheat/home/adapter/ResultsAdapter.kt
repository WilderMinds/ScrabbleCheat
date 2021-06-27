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
import timber.log.Timber

class ResultsAdapter(var horizontal: Boolean = false, private val clickListener: ItemClickListener) : ListAdapter<WordResult, ResultsAdapter.ItemViewHolder>(DiffCallback()) {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = if (horizontal) {
            ItemLetterScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }
        return ItemViewHolder(binding, horizontal, clickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.hashCode()?.toLong() ?: position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }


    override fun onCurrentListChanged(
        previousList: MutableList<WordResult>,
        currentList: MutableList<WordResult>
    ) {
        super.onCurrentListChanged(previousList, currentList)

        Timber.e("previousList -> $previousList")
        Timber.e("currentList -> $currentList")
    }

    class ItemViewHolder(
        private val binding: ViewDataBinding,
        private val horizontal: Boolean,
        private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

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