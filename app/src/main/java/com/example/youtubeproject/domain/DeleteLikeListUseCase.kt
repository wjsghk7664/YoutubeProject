package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.VideoModel
import javax.inject.Inject

class DeleteLikeListUseCase @Inject constructor(private val remoteUserLikeListRepository: RemoteUserLikeListRepository) {
    operator fun invoke(id:String, videoModel: VideoModel, callback:(Boolean)->Unit){
        remoteUserLikeListRepository.DeleteLike(id, videoModel){
            callback(it)
        }
    }
}