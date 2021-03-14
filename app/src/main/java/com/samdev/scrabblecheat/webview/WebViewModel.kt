package com.samdev.scrabblecheat.webview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel: ViewModel() {
    val selectedWord = MutableLiveData<String>()
    val connected = MutableLiveData<Boolean>()
}