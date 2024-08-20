package com.example.youtubeproject.domain

import androidx.activity.OnBackPressedCallback
import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.remote.youtube.CategoryVideoResult
import javax.inject.Inject


class GetUserLikesUseCase @Inject constructor(
    private val remoteUserLikeListRepository: RemoteUserLikeListRepository
) {
    operator fun invoke(userId: String, callback: (LikeList?) -> (Unit)) {
        remoteUserLikeListRepository.getList(userId, callback)
    }
}