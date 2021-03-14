package com.samdev.scrabblecheat.home

import com.samdev.scrabblecheat.model.WordResult

interface ItemClickListener {
    fun onItemClicked(item: WordResult)
}