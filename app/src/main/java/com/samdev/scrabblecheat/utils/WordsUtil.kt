package com.samdev.scrabblecheat.utils

import com.samdev.scrabblecheat.App
import com.samdev.scrabblecheat.R
import java.io.BufferedReader
import java.io.InputStreamReader

class WordsUtil {

    // source: http://www.wordfind.com/scrabble-letter-values/
    var letterScores = mutableMapOf(
            Pair("A", 1),
            Pair("B", 3),
            Pair("C", 3),
            Pair("D", 2),
            Pair("E", 1),
            Pair("F", 4),
            Pair("G", 2),
            Pair("H", 4),
            Pair("I", 1),
            Pair("J", 8),
            Pair("K", 5),
            Pair("L", 1),
            Pair("M", 3),
            Pair("N", 1),
            Pair("O", 1),
            Pair("P", 3),
            Pair("Q", 10),
            Pair("R", 1),
            Pair("S", 1),
            Pair("T", 1),
            Pair("U", 1),
            Pair("V", 4),
            Pair("W", 4),
            Pair("X", 8),
            Pair("Y", 4),
            Pair("Z", 10)
    )

    fun readFileFromRaw(): List<String> {
        val resources = App.instance.resources
        val stream = resources.openRawResource(R.raw.dictionary)
        return BufferedReader(InputStreamReader(stream)).readLines()
    }

}