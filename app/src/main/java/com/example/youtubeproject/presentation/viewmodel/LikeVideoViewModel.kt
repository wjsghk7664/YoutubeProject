package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import android.widget.Toast
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.domain.GetUserLikesUseCase
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LikeVideoViewModel(
    private val getUserLikeUseCase: GetUserLikesUseCase
) {
    fun getUserLikesVideos(userId: String, callback: (LikeList?) -> (Unit)) {
        runCatching {
            getUserLikeUseCase.invoke(userId, callback)
        }.onFailure {
            Log.d("LikeVideoViewModel", "getUserLikesVideos: Fail to load likes videos.")
        }
    }
}