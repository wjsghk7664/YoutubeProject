package com.example.youtubeproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.youtubeproject.data.model.CategoryChannelModel

@Entity(tableName = "cachechannel_table")
data class CacheChannel(
    @PrimaryKey
    val category:String,
    val categoryChannelModel: CategoryChannelModel //저장할때는 마지막 페이지 토큰과 전체 아이템을 합침
)