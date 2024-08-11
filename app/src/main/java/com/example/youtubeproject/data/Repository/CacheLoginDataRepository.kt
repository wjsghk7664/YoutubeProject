package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.CacheLogin

interface CacheLoginDataRepository {
    fun getUserData():Result<CacheLogin>
    fun insertUserData(cacheLogin: CacheLogin)
    fun deleteUserData()
}