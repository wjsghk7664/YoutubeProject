package com.example.youtubeproject.data.model

data class CategoryVideoResponse(
    val id: String?,
    val snippet: VideoSnippet?,
    val contentDetails: VideoContentDetails?,
    val statistics: StatisticsVideo?
)

data class VideoSnippet(
    val publishedAt: String?,
    val channelId: String?,
    val title: String?,
    val description: String?,
    val thumbnails: Thumbnails?,
    val channelTitle: String?,
    val tags: List<String> = emptyList(),
    val categoryId: String?,
)



data class VideoContentDetails(
    val duration: String?,
    val dimension: String?,
    val definition: String?,
    val caption: String?,
)

data class StatisticsVideo(
    val viewCount: Long?,
    val likeCount: Long?,
    val dislikeCount: Long?,
    val commentCount: Long?
)
