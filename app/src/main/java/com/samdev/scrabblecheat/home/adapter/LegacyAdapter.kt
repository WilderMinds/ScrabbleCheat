package com.samdev.scrabblecheat.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.samdev.scrabblecheat.databinding.ItemLetterScoreBinding
import com.samdev.scrabblecheat.databinding.ItemResultBinding
import com.samdev.scrabblecheat.home.ItemClickListener
import com.samdev.scrabblecheat.model.WordResult

class LegacyAdapter(
    private var list: MutableList<WordResult>,
    private val horizontal: Boolean = false,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<LegacyAdapter.LegacyViewHolder>() {

    private lateinit var binding: ViewDataBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegacyViewHolder {
        binding = if (horizontal) {
            ItemLetterScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        }

        return LegacyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LegacyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(newList: List<WordResult>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class LegacyViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
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