package com.example.youtubeproject.data.model

data class CategoryVideoModel (
    val nextPageToken:String?,
    val items: List<CategoryVideoResponse>
)