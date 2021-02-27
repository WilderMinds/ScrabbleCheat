package com.samdev.scrabblecheat.model

data class WordResult(
    val word: String,
    val score: Int,
    val readableScore: String = "$score pts"
)
