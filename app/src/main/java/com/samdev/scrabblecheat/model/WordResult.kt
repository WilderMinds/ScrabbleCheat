package com.samdev.scrabblecheat.model

internal data class WordResult(
    val word: String,
    val score: Int,
    val readableScore: String = "$score pts"
) {
    override fun toString(): String {
        return "{$word=$score}"
    }
}
