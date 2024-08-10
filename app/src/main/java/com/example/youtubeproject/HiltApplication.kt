package com.example.youtubeproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApplication: Application() {

    override fun onCreate() {
        super.onCreate()


    }
}