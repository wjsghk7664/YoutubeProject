package com.example.youtubeproject.data.model

data class Playlist(
    val id: Long,
    val title: String,
) {
    //TODO: Change 'Long' to 'Video'
    val lists = listOf<Long>()
    val size = lists.size
}
