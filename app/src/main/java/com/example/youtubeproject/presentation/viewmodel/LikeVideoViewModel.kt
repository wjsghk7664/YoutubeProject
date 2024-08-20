package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.VideoModel
import com.example.youtubeproject.domain.AddLikeUseCase
import com.example.youtubeproject.domain.DeleteLikeUseCase
import com.example.youtubeproject.domain.GetUserLikesUseCase
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LikeVideoViewModel(
    private val getUserLikeUseCase: GetUserLikesUseCase,
    private val addLikesUseCase: AddLikeUseCase,
    private val deleteLikeUseCase: DeleteLikeUseCase
) {
    fun getUserLikesVideos(userId: String, callback: (LikeList?) -> (Unit)) {
        runCatching {
            getUserLikeUseCase.invoke(userId, callback)
        }.onFailure {
            Log.d("LikeVideoViewModel", "getUserLikesVideos: Fail to load likes videos.")
        }
    }

    fun addUserLikesVideos(userId: String, newVideo: VideoModel, callback: (Boolean) -> (Unit)) {
        runCatching {
            addLikesUseCase.invoke(userId, newVideo, callback)
        }.onFailure {
            Log.d("LikeVideoViewModel", "addLikesUseCase: Fail to add likes videos.")
        }
    }

    fun deleteUserLikesVideos(userId: String, callback: (Boolean) -> (Unit)) {
        runCatching {
            deleteLikeUseCase.invoke(userId, callback)
        }.onFailure {
            Log.d("LikeVideoViewModel", "deleteUserLikesVideos: Fail to delete likes videos.")
        }
    }
}