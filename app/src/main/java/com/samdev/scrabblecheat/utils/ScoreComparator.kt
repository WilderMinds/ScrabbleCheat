package com.samdev.scrabblecheat.utils

import com.samdev.scrabblecheat.model.WordResult

class ScoreComparator: Comparator<WordResult> {
    override fun compare(p0: WordResult?, p1: WordResult?): Int {
        val score0 = p0?.score ?: 0
        val score1 = p1?.score ?: 0

        return score1.minus(score0)
    }
}