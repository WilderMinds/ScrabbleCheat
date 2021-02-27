package com.samdev.scrabblecheat.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samdev.scrabblecheat.databinding.ItemLetterScoreBinding
import com.samdev.scrabblecheat.model.WordResult

class LetterScoreAdapter : ListAdapter<WordResult, LetterScoreAdapter.LetterScoreItemViewHolder>(
    DiffCallback()
) {

    private lateinit var binding: ItemLetterScoreBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LetterScoreItemViewHolder {
        binding = ItemLetterScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterScoreItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterScoreItemViewHolder, position: Int) {
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

    inner class LetterScoreItemViewHolder(binding: ItemLetterScoreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WordResult) = with(itemView) {

            binding.letter = item

            setOnClickListener {
                // TODO: Handle on click (Maybe View word definition)
            }
        }
    }
}