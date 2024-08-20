package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import com.example.youtubeproject.data.model.LikeList
import javax.inject.Inject

class GetLikeListUseCase @Inject constructor(private val remoteUserLikeListRepository: RemoteUserLikeListRepository) {
    operator fun invoke(id:String, callback:(LikeList?)->Unit){
        remoteUserLikeListRepository.getList(id){
            callback(it)
        }
    }
}