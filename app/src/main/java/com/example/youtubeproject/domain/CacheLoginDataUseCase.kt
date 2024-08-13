package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.CacheLoginDataRepository
import javax.inject.Inject

class CacheLoginDataUseCase @Inject constructor(private val cacheLoginDataRepository: CacheLoginDataRepository) {
    operator fun invoke(id:String, password:String):Boolean?{
        return cacheLoginDataRepository.saveLoginData(id, password).getOrNull()
    }
}