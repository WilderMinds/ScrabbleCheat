package com.samdev.scrabblecheat.utils

import com.samdev.scrabblecheat.model.WordResult
import java.util.*
import kotlin.collections.ArrayList

class ScrabbleHelper(private var input: String, private val allowTwoLetterWords: Boolean = false) {

    private var inputMap: Map<String, Int>
    private val scoreMap = WordsUtil().letterScores
    private val dictionary =  WordsUtil().readFileFromRaw()
            /*.apply {
        trimDictionary(this)
    }*/


    init {
        input = input.toLowerCase(Locale.ROOT)
        inputMap = reorganizeInput()
    }


    /**
     * Based on the letters observed in the input string, we will want to
     * trim the dictionary list to contain only the words that are relevant
     * to the input string.
     *
     * eg: if we have inputString "abcd", we do not want the algorithm going
     * through the 'e' - 'f' section of the dictionary, as its a waste of resources.
     * Hence we trim.
     */
    private fun trimDictionary(entireDictionary: List<String>): List<String> {
        val result = mutableListOf<String>()

        // sort into map with starting letter as key
        val map = mutableMapOf<String, MutableList<String>>()


        return result
    }


    fun getLetterScores() : List<WordResult> {
        val list = mutableListOf<WordResult>()
        inputMap.keys.forEach {
            val letter = it.toUpperCase(Locale.ROOT)
            list.add(WordResult(letter, scoreMap[letter]!!))
        }
        return list
    }


    fun generateWords(): List<WordResult> {
        // method 1 (Brute Force)
        return ArrayList(bruteForce())
    }


    /**
     * Use brute force
     * Go through each letter all the words in the dictionary while
     * matching letters to find matches
     */
    private fun bruteForce(): MutableSet<WordResult> {

        // use a set to avoid insertion of duplicates
        val result = mutableSetOf<WordResult>()

        // each dictionary word
        for (it in dictionary) {

            // avoid incorrect matches due to letter-case variance
            val word = it.toLowerCase()

            // length of letters in the current dictionary word
            val lengthOfWord = word.length

            if (!allowTwoLetterWords && lengthOfWord < 3) {
                continue
            }


            // track how many letter matches are found per word
            var matchCount = 0


            // renew the input map for each word
            val scopeMap: MutableMap<String, Int> = mutableMapOf()
            scopeMap.putAll(inputMap)


            // for each letter in word
            for (i in word.indices) {

                val char = word[i].toString()

                // if we find [lengthOfWord] letter matches, then we save that
                // word as a possible match
                if (isLetterMatch(char, scopeMap)) {
                    matchCount++
                }

                // we have already found a match, exit loop
                if (matchCount >= lengthOfWord) {

                    // calculate score and insert into results
                    result.add(createResult(word))
                    break
                }
            }

        }


        prettyPrint(input, result)
        return result
    }


    private fun createResult(word: String): WordResult {
        val score = calculateScore(word)
        return WordResult(word, score)
    }



    private fun calculateScore(word: String): Int {
        var score = 0

        for (i in word.indices) {
            val letter = word[i].toString().toUpperCase()
            score += scoreMap[letter]!!
        }

        return score
    }


    /**
     * Reorganize input letter into map, taking into consideration
     * the possibility of multiple occurrences of certain letters
     */
    private fun reorganizeInput(): MutableMap<String, Int> {

        val inputMap = mutableMapOf<String, Int>()

        // remove spaces
        input = input.replace("\\s".toRegex(), "")

        // remove non alphabets
        input = input.replace("[^A-Za-z]".toRegex(), "")

        for (i in input.indices) {
            val char = input[i].toString()

            // increase occurrence if exists or add entry
            if (inputMap.containsKey(char)) {
                inputMap[char] = inputMap[char]!!.inc()
            } else {
                inputMap[char] = 1
            }
        }

        println("initial input map => $inputMap")
        return inputMap
    }


    /**
     * Check if the letter exists in the input map
     * Make sure to check occurrences as well buy updating input map as
     * letters are checked and "crossed-off" (removed from map)
     */
    private fun isLetterMatch(letter: String, scopeMap: MutableMap<String, Int>): Boolean {

        // does letter exist in input string
        if (scopeMap.containsKey(letter)) {

            // if value is '0', then there are no more occurrences of that
            // particular letter
            if (scopeMap[letter] == 0) {
                return false
            }

            // Match found, reduce occurrence index
            scopeMap[letter] = scopeMap[letter]!!.dec()

            return true
        }

        // letter not in input string
        return false
    }



    private fun prettyPrint(input: String, result: MutableSet<WordResult>) {
        println("Input String => $input")
        println("\n")

        val resultArray = ArrayList(result)

        Collections.sort(resultArray, ScoreComparator())

        resultArray.forEach {
            println("word => ${it.word}")
            println("score => ${it.score}")
            println("")
        }
    }


    /**
     * This method can only be invoked when, inputString length > 8
     * User has fewer letters in his rack and picks a random letter
     * from the sack and adds to his
     */
    @Deprecated(message = "User can always give a new input string and call the 'generate()' method again")
    private fun desperateAction() {

    }
}


