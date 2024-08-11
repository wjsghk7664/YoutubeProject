package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.CacheLogin
import com.example.youtubeproject.data.local.CacheLoginDao
import javax.inject.Inject

class CacheLoginDataRepositoryImpl @Inject constructor(private val cacheLoginDao: CacheLoginDao):CacheLoginDataRepository {
    override fun getUserData(): Result<CacheLogin> {
        return runCatching {
            cacheLoginDao.getLoginCache()
        }
    }

    override fun insertUserData(cacheLogin: CacheLogin) {
        cacheLoginDao.insertLoginCache(cacheLogin)
    }

    override fun deleteUserData() {
        cacheLoginDao.deleteLoginCache()
    }
}