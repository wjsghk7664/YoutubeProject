package com.example.youtubeproject.data.model

data class CategoryChannelResponse(
    val id:String?,
    val snippet: ChannelSnippet?,
    val contentDetails:ChannelContentDetails?,
    val statistics:StatisticsChannel?,
    val brandingSettings:BrandingSettings?
)

data class ChannelSnippet(
    val title: String?,
    val description:String?,
    val publishedAt: String?,
    val thumbnails: Thumbnails?
)

data class ChannelContentDetails(
    val relatedPlaylists:RelatedPlaylists?
)

data class RelatedPlaylists(
    val likes:String,
    val favorites:String,
    val uploads:String
)

data class StatisticsChannel(
    val viewCount:Long,
    val subscriberCount:Long,
    val hiddenSubscriberCount:Boolean,
    val videoCount:Long
)

data class BrandingSettings(
    val channel: Channel?
)

data class Channel(
    val title: String?,
    val description: String?,
    val keywords: String?,
)