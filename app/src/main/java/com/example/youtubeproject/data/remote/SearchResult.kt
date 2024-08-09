package com.example.youtubeproject.data.remote

import com.example.youtubeproject.data.model.SearchResultModel
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchResult {
    @GET("v3/search")
    suspend fun getSearchResult(
        @Query("part") part: String ="snippet",
        @Query("q") q:String,
        @Query("type") type:String ="video",
        @Query("videoCategoryId") videoCategoryId:String? =null,
        @Query("pageToken") pageToken:String? = null
    ):SearchResultModel
}