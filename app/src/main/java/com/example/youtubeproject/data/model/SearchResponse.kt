package com.example.youtubeproject.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("id") val id: Id?,
    @SerializedName("snippet") val snippet:SearchSnippet?
) {
    fun toVideoModel(): VideoModel
        = VideoModel(
        id = id!!.videoId!!,
        snippet = Snippet(
            publishedAt = snippet!!.publishedAt,
            channelId = snippet.channelId,
            title = snippet.title,
            description = snippet.description,
            thumbnails = snippet.thumbnails,
            channelTitle = snippet.channelTitle
        )

    )

}

data class Id(
    @SerializedName("kind") val kind:String?,
    @SerializedName("videoId") val videoId:String?,
)

data class SearchSnippet(
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("channelId") val channelId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description:String?,
    @SerializedName("thumbnails") val thumbnails: Thumbnails?,
    @SerializedName("channelTitle") val channelTitle:String?,
)

data class Thumbnails(
    @SerializedName("high") val high:Thumbnail? = null
)

data class Thumbnail(
    @SerializedName("url") val url:String? = "",
    @SerializedName("width") val width: String? = "",
    @SerializedName("height") val height: String? = ""
)