package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import javax.inject.Inject

class CreatePlayListsUseCase @Inject constructor(private val remoteUserPlayListRepository: RemoteUserPlayListRepository) {
    operator fun invoke(id:String, callback:(Boolean)->Unit){
        remoteUserPlayListRepository.CreatePlayLists(id){
            callback(it)
        }
    }
}