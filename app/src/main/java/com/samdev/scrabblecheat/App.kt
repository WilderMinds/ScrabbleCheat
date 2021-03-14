package com.samdev.scrabblecheat

import android.app.Application
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber

class App: Application() {

    init {
        instance = this
    }

    companion object {
        lateinit var instance: App
    }


    override fun onCreate() {
        super.onCreate()

        // default light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    fun isWebViewAvailable(): Boolean {
        val pm = packageManager
        return try {
            pm.getPackageInfo("com.google.android.webview", PackageManager.GET_META_DATA)
            println("webView is available")
            true
        } catch (e: PackageManager.NameNotFoundException) {
            println("webView unavailable")
            false
        }
    }


    fun generateDictionaryUrl(word: String): String {
        val queryUrl = "https://www.collinsdictionary.com/dictionary/english/$word"
        //val queryUrl = "https://www.google.com/search?q=dictionary&ie=UTF-8#dobs=$word"

        return queryUrl
    }
}