package com.jxlopez.kitsuapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}