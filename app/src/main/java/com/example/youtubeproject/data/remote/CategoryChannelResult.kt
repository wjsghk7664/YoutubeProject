package com.example.youtubeproject.data.remote

import com.example.youtubeproject.data.model.CategoryChannelModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryChannelResult {
    @GET("v3/channels")
    suspend fun getCategoryChannel(
        @Query("part") part:String = "snippet",
        @Query("id")  id:String,
        @Query("pageToken") pageToken:String? = null
    ):CategoryChannelModel
}