package com.example.youtubeproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cachelikelist_table")
data class CacheLikeList (
    @PrimaryKey
    val id: String, //VideoModel의 Id
    val likeItem: VideoModel
)