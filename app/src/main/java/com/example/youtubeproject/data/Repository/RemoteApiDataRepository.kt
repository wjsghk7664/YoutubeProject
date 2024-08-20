package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.SearchResultModel

interface RemoteApiDataRepository {
    suspend fun getPopularResult(page: String?):Result<CategoryVideoModel>
    suspend fun getSearchResult(query: String, page:String?, category: String?):Result<SearchResultModel>
    suspend fun getCategoryVideoResult(category: String,page: String?):Result<CategoryVideoModel>
    suspend fun getCategoryChannelResult(channelId: String, page: String?):Result<CategoryChannelModel>
    suspend fun getChannelThumbnail(channelId: String): Result<String>

}