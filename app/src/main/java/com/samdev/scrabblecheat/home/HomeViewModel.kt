package com.samdev.scrabblecheat.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdev.scrabblecheat.model.WordResult
import com.samdev.scrabblecheat.utils.ScrabbleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel : ViewModel() {

    val allow2LetterWords = MutableLiveData<Boolean>()
    val searchInput = MutableLiveData<String>()
    val selectedWord = MutableLiveData<String>()
    val connected = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    var results = MutableLiveData<List<WordResult>>()
    var letterScores = MutableLiveData<List<WordResult>>()

    private var inSearchState: Boolean = false


    init {
        allow2LetterWords.postValue(false)
        connected.postValue(false)
        loading.postValue(false)
    }


    /**
     * Search words or Clear state
     */
    fun action() {
        if (inSearchState) {
            clearState()
        } else {
            search()
        }
    }


    /**
     * Search for words
     */
    fun search() {
        Timber.e("search input = ${searchInput.value}")
        // results.postValue(arrayListOf())

        viewModelScope.launch(Dispatchers.IO) {
            searchInput.value?.let {
                loading.postValue(true)

                val scrabbleHelper = ScrabbleHelper(it, allow2LetterWords.value ?: false)
                val letterScores = scrabbleHelper.getLetterScores()
                val output = scrabbleHelper.generateWords()

                updateLetterScore(letterScores)
                updateList(output)

                loading.postValue(false)
                inSearchState = true
            }
        }
    }


    /**
     * Clear state
     */
    private fun clearState() {
        searchInput.postValue("")
        updateLetterScore(emptyList())
        updateList(emptyList())
        inSearchState = false
    }


    private fun updateList(list: List<WordResult>) {
        results.postValue(list)
    }

    private fun updateLetterScore(list: List<WordResult>) {
        letterScores.postValue(list)
    }
}