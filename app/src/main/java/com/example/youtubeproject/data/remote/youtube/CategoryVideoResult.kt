package com.example.youtubeproject.data.remote.youtube

import com.example.youtubeproject.data.model.CategoryVideoModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryVideoResult {
    @GET("v3/videos")
    suspend fun getCategoryVideo(
        @Query("part") part: String ="snippet",
        @Query("chart") type:String ="mostPopular",
        @Query("videoCategoryId") videoCategoryId:String,
        @Query("pageToken") pageToken:String? = null
    ):CategoryVideoModel
}