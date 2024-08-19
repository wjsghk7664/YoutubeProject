package com.example.youtubeproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Playlist(
    val id: Long,       //TODO: id 바꾸기 / TimeStamp or String ...
    val title: String,
    val lists: List<VideoModel> = listOf(),   //TODO: Change 'Long' to 'Video'
    val size: Int = lists.size
)
