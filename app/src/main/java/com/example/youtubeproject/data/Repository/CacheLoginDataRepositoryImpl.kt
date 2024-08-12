package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.local.CacheLoginDataSource
import javax.inject.Inject

class CacheLoginDataRepositoryImpl @Inject constructor(private val cacheLoginDataSource: CacheLoginDataSource):CacheLoginDataRepository {
    override fun saveLoginData(id: String, password: String): Result<Boolean> {
        return runCatching {
            cacheLoginDataSource.saveLoginData(id,password)
        }
    }

    override fun getLoginData(): Result<Pair<String?, String?>> {
        return runCatching {
            cacheLoginDataSource.getLoginData()
        }
    }

    override fun deleteLoginData(): Result<Boolean> {
        return runCatching {
            cacheLoginDataSource.deleteLoginData()
        }
    }
}