package com.example.youtubeproject.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.youtubeproject.data.model.CategoryVideoModel


@Entity(tableName = "cachevideo_table")
data class CacheVideo(
    @PrimaryKey
    val category:String,
    val categoryVideoModel: CategoryVideoModel //저장할때는 마지막 페이지 토큰과 전체 아이템을 합침
)