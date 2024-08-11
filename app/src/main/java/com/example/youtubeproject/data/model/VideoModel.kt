package com.example.youtubeproject.data.model

data class VideoModel(
    val id:String="",
    val snippet: Snippet=Snippet(),
)

data class Snippet(
    val publishedAt: String? =null,
    val channelId: String? =null,
    val title: String? =null,
    val description:String? = null,
    val thumbnails: Thumbnails? =null,
    val channelTitle:String? =null,
)

fun SearchToVideoModel(searchResponse: SearchResponse):VideoModel{
    val id=searchResponse.id?.video?:""
    val searchSnippet=searchResponse.snippet
    var snippet = Snippet()
    searchSnippet?.run {
        snippet=Snippet(
            publishedAt,
            channelId,
            title,
            description,
            thumbnails,
            channelTitle
        )
    }
    return VideoModel(id,snippet)
}

fun CategoryVideoToVideoModel(categoryVideoResponse: CategoryVideoResponse):VideoModel{
    val id=categoryVideoResponse.id?:""
    val categoryVideoSnippet = categoryVideoResponse.snippet
    var snippet = Snippet()
    categoryVideoSnippet?.run {
        snippet=Snippet(
            publishedAt,
            channelId,
            title,
            description,
            thumbnails,
            channelTitle
        )
    }
    return VideoModel(id,snippet)
}