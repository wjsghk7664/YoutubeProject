package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.CacheLoginDataRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val cacheLoginDataRepository: CacheLoginDataRepository) {
    operator fun invoke():Boolean{
        return cacheLoginDataRepository.deleteLoginData().getOrNull()?:false
    }
}