package com.example.youtubeproject

import android.app.Application
import android.util.Log
import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import com.example.youtubeproject.data.model.Snippet
import com.example.youtubeproject.data.model.VideoModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HiltApplication: Application() {

    override fun onCreate() {
        super.onCreate()

    }
}