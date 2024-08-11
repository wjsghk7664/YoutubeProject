package com.example.youtubeproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.youtubeproject.data.model.User


@Entity(tableName = "cachelogin_table")
data class CacheLogin(
    @PrimaryKey
    val key: String = "myLoginData",
    val id: String,
    val password: String
)