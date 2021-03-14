package com.samdev.scrabblecheat.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("adapter")
fun RecyclerView.bindAdapter(ad: RecyclerView.Adapter<*>?) {
    this.run {
        ad?.let {
            setHasFixedSize(true)
            adapter = it
        }
    }
}