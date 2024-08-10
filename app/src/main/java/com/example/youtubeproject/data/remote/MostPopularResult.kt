package com.example.youtubeproject.data.remote

import com.example.youtubeproject.data.model.CategoryVideoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MostPopularResult {
    @GET("v3/videos")
    suspend fun getPopular(
        @Query("part") part: String ="snippet",
        @Query("chart") type:String ="mostPopular",
        @Query("pageToken") pageToken:String? = null
    ):CategoryVideoModel
}