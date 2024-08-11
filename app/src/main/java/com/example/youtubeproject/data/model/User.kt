package com.example.youtubeproject.data.model

data class User(
    val name:String,
    val id:String,
    val password:String
    val likeList:List<VideoModel>
)