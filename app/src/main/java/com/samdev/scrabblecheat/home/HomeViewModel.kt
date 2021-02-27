package com.samdev.scrabblecheat.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samdev.scrabblecheat.model.WordResult
import com.samdev.scrabblecheat.utils.ScrabbleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HomeViewModel : ViewModel() {

    var allow2LetterWords: Boolean = false
    var searchInput = MutableLiveData<String>()

    var results = MutableLiveData<List<WordResult>>()
    var letterScores = MutableLiveData<List<WordResult>>()


    fun search() {
        Timber.e("search input = ${searchInput.value}")
        // results.postValue(arrayListOf())

        viewModelScope.launch(Dispatchers.IO) {
            searchInput.value?.let {

                val scrabbleHelper = ScrabbleHelper(it, allow2LetterWords)
                val letterScores = scrabbleHelper.getLetterScores()
                val output = scrabbleHelper.generateWords()

                updateLetterScore(letterScores)
                updateList(output)
            }
        }
    }


    private fun updateList(list: List<WordResult>) {
        results.postValue(list)
    }

    private fun updateLetterScore(list: List<WordResult>) {
        letterScores.postValue(list)
    }
}