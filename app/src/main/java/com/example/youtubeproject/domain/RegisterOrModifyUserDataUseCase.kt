package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteUserDataRepository
import com.example.youtubeproject.data.model.User
import javax.inject.Inject

class RegisterOrModifyUserDataUseCase @Inject constructor(private val remoteUserDataRepository: RemoteUserDataRepository) {
    operator fun invoke(user: User, callback:(Boolean)->Unit){
        remoteUserDataRepository.AddOrModifyUserData(user){
            callback(it)
        }
    }
}