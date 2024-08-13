package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.CacheLoginDataRepository
import javax.inject.Inject

class GetCacheLoginDataUseCase @Inject constructor(private val cacheLoginDataRepository: CacheLoginDataRepository) {
    operator fun invoke():Pair<String?,String?>?{
        return cacheLoginDataRepository.getLoginData().getOrNull()
    }
}