package com.example.youtubeproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cachelogin_table")
data class CacheLogin(
    @PrimaryKey
    val key: String = "myLoginData",
    val id: String,
    val password: String
)