package com.example.youtubeproject.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    val id: Long,
    val title: String,
    val lists: List<Long> = listOf(),   //TODO: Change 'Long' to 'Video'
    val size: Int = lists.size
): Parcelable
