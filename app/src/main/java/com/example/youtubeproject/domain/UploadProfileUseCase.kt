package com.example.youtubeproject.domain

import android.graphics.Bitmap
import com.example.youtubeproject.data.Repository.RemoteImageRepository
import javax.inject.Inject

class UploadProfileUseCase @Inject constructor(private val remoteImageRepository: RemoteImageRepository) {
    operator fun invoke(bitmap: Bitmap, id:String, callback:(String?)->Unit){
        remoteImageRepository.uploadImage(bitmap,id){bool,url ->
            if(bool){
                callback(url)
            }else{
                callback(null)
            }
        }
    }
}