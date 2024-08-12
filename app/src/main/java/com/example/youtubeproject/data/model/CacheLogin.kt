package com.example.youtubeproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cachelogin_table")
data class CacheLogin(
    @PrimaryKey
    val key: String = "myLoginData",
    val lastLogin:Long, //마지막 로그인 시점이 파이어베이스에 저장된 값과 다르면 캐싱된 좋아요 리스트 업데이트
    val id: String,
    val password: String,
    val name:String
)