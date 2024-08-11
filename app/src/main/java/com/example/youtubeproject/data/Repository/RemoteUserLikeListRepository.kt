package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.VideoModel

interface RemoteUserLikeListRepository {
    fun AddLike(id:String, videoModel: VideoModel, callback:(Boolean)->Unit)
    fun DeleteLike(id:String,videoModel: VideoModel, callback: (Boolean) -> Unit)
    fun createList(id:String, callback: (Boolean) -> Unit)
    fun deleteList(id:String, callback: (Boolean) -> Unit)
}