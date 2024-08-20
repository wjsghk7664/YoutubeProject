package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.youtubeproject.data.model.Id
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.domain.AddLikeUseCase

class VideoDetailViewModel(
    private val addLikeUseCase: AddLikeUseCase
): ViewModel() {

    var videoModel: VideoModel? = null

    fun addLike(userId: String, callback: (Boolean) -> (Unit)) {
        runCatching {
            addLikeUseCase.invoke(userId, videoModel!!, callback)
        }.onFailure {
            Log.d("VideoDetailViewModel", "addLike onFailure")
        }
    }
}