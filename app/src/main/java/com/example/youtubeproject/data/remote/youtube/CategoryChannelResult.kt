package com.example.youtubeproject.data.remote.youtube

import com.example.youtubeproject.data.model.CategoryChannelModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryChannelResult {
    @GET("v3/channels")
    suspend fun getCategoryChannel(
        @Query("part") part:String = "snippet",
        @Query("id")  id:String, //쉼표로 구분해서 여러개 아이디들 넣을 수 있음
        @Query("pageToken") pageToken:String? = null
    ):CategoryChannelModel
}