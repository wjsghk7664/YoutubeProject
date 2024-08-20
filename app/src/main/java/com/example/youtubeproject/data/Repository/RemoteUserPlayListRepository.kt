package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.UserPlayLists

interface RemoteUserPlayListRepository {
    fun CreatePlayLists(id:String, callback:(Boolean)->Unit)
    fun DeletePlayLists(id:String, callback: (Boolean) -> Unit)
    fun AddPlayList(id:String, playlist: Playlist, callback: (Boolean) -> Unit)
    fun deletePlayList(id:String, playlist: Playlist, callback: (Boolean) -> Unit)
    fun getAllPlayLists(id:String, callback: (UserPlayLists?) -> Unit)
}