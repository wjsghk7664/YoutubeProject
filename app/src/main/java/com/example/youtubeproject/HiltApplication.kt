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

    @Inject lateinit var remoteUserLikeListRepository: RemoteUserLikeListRepository

    override fun onCreate() {
        super.onCreate()

        remoteUserLikeListRepository.AddLike("111", VideoModel("1",Snippet(title = "1"))){}
        remoteUserLikeListRepository.AddLike("111", VideoModel("2",Snippet(title = "2"))){}
        remoteUserLikeListRepository.AddLike("111", VideoModel("3",Snippet(title = "3"))){}
        remoteUserLikeListRepository.AddLike("111", VideoModel("4",Snippet(title = "4"))){}
    }
}