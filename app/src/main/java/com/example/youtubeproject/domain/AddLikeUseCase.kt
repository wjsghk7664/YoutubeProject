package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import com.example.youtubeproject.data.model.VideoModel
import javax.inject.Inject

class AddLikeUseCase @Inject constructor(
    private val remoteUserLikeListRepository: RemoteUserLikeListRepository
) {
    operator fun invoke(userId: String, newVideo: VideoModel, callback: (Boolean) -> (Unit)) {
        remoteUserLikeListRepository.AddLike(userId, newVideo, callback)
    }
}