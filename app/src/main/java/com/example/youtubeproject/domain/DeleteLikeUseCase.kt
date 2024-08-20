package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import javax.inject.Inject

class DeleteLikeUseCase @Inject constructor(
    private val remoteUserLikeListRepository: RemoteUserLikeListRepository
) {
    operator fun invoke(userId: String, callback: (Boolean) -> (Unit)) {
        remoteUserLikeListRepository.deleteList(userId, callback)
    }
}