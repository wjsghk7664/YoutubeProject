package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.local.CacheLikeListDao
import com.example.youtubeproject.data.model.VideoModel
import javax.inject.Inject

class CacheLikeListRepositoryImpl @Inject constructor(
    private val cacheLikeListDao: CacheLikeListDao
):CacheLikeListRepository {
    override suspend fun getCacheLikeListResult(): Result<List<VideoModel>> {
        return runCatching {
            cacheLikeListDao.getCacheLikeList()
        }
    }

    override suspend fun insertCacheLikeItem(videoModel: VideoModel) {
        cacheLikeListDao.insertCacheLikeItem(videoModel)
    }

    override suspend fun deleteCacheLikeItem(itemId: String) {
        cacheLikeListDao.deleteCacheLikeItem(itemId)
    }

    override suspend fun deleteCacheLikeList() {
        cacheLikeListDao.deleteCacheLikeList()
    }
}