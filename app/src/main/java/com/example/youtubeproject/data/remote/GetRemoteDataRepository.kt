package com.example.youtubeproject.data.remote

import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.SearchResultModel

interface GetRemoteDataRepository {
    suspend fun getPopularResult(page: String?):Result<CategoryVideoModel>
    suspend fun getSearchResult(query: String, page:String?, category: String?):Result<SearchResultModel>
    suspend fun getCategoryVideoResult(category: String,page: String?):Result<CategoryVideoModel>
    suspend fun getCategoryChannelResult(category: String, page: String?):Result<CategoryChannelModel>
}