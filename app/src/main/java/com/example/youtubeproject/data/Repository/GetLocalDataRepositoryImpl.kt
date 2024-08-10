package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.local.CacheChannel
import com.example.youtubeproject.data.local.CacheChannelDao
import com.example.youtubeproject.data.local.CacheVideo
import com.example.youtubeproject.data.local.CacheVideoDao
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.SearchResultModel
import javax.inject.Inject

class GetLocalDataRepositoryImpl @Inject constructor(
    private val cacheChannelDao: CacheChannelDao,
    private val cacheVideoDao: CacheVideoDao
):GetLocalDataRepository {

    override suspend fun getCategoryVideoResult(
        category: String,
        page: String?
    ): Result<CategoryVideoModel> {
        return runCatching {
            cacheVideoDao.getCacheVideo(category).categoryVideoModel
        }
    }

    override suspend fun getCategoryChannelResult(
        category: String,
        page: String?
    ): Result<CategoryChannelModel> {
        return runCatching {
            cacheChannelDao.getCacheChannel(category).categoryChannelModel
        }
    }

    override suspend fun insertCategoryVideo(
        categoryVideoModel: CategoryVideoModel,
        category: String
    ) {
        cacheVideoDao.insertVideoChannel(CacheVideo(category,categoryVideoModel))
    }

    override suspend fun insertCategoryChannel(
        categoryChannelModel: CategoryChannelModel,
        category: String
    ) {
        cacheChannelDao.insertCacheChannel(CacheChannel(category,categoryChannelModel))
    }

    override suspend fun deleteCategoryVideo() {
        cacheVideoDao.deleteAllCacheVideo()
    }

    override suspend fun deleteCategoryChannel() {
        cacheChannelDao.deleteAllCacheChannel()
    }
}