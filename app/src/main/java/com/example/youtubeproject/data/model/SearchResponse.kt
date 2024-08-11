package com.example.youtubeproject.data.model

data class SearchResponse(
    val id: Id?,
    val snippet:searchSnippet?
)

data class Id(
    val kind:String?,
    val video:String?,
)

data class searchSnippet(
    val publishedAt: String?,
    val channelId: String?,
    val title: String?,
    val description:String?,
    val thumbnails: Thumbnails?,
    val channelTitle:String?,
)

data class Thumbnails(
    val default:Thumbnail?
)

data class Thumbnail(
    val url:String?,
    val width: String?,
    val height: String?
)