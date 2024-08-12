package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.VideoModel

interface CacheLikeListRepository {
    suspend fun getCacheLikeListResult():Result<List<VideoModel>>
    suspend fun insertCacheLikeItem(videoModel: VideoModel)
    suspend fun deleteCacheLikeItem(itemId:String)
    suspend fun deleteCacheLikeList()
}