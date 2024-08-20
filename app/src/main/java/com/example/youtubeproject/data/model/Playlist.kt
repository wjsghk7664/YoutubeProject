package com.example.youtubeproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Playlist(
    val id: String = "",
    val title: String = "",
    val lists: List<VideoModel> = listOf(),
    val size: Int = lists.size
)


