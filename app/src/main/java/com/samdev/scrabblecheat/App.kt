package com.samdev.scrabblecheat

import android.app.Application
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
}