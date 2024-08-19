package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserLikeListRepository
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CreateLikeListUseCase @Inject constructor(private val remoteUserLikeListRepository: RemoteUserLikeListRepository) {
    operator fun invoke(id:String, callback:(Boolean)->Unit){
        remoteUserLikeListRepository.createList(id){
            callback(it)
        }
    }
}