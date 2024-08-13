package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserDataRepository
import com.example.youtubeproject.data.model.User
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(private val remoteUserDataRepository: RemoteUserDataRepository)  {
    operator fun invoke(id:String, password:String, callback:(User?)->Unit){
        remoteUserDataRepository.Login(id,password){
            callback(it)
        }
    }
}