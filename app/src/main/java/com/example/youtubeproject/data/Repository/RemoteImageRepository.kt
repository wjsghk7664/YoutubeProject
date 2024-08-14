package com.example.youtubeproject.data.Repository

import android.graphics.Bitmap

interface RemoteImageRepository {
    fun uploadImage(bitmap: Bitmap, id:String,callback:(Boolean, String?)->Unit)
    fun deleteImage(id:String, callback:(Boolean)->Unit)
}