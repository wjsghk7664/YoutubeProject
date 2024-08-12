package com.example.youtubeproject.data.model

data class User(
    val name:String ="",
    val id:String="",
    val password:String="",
    val lastLogin:Long = 0L
)