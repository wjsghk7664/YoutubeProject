package com.example.youtubeproject.data.Repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.http.Url
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class RemoteImageRepositoryImpl @Inject constructor(private val storage: FirebaseStorage):RemoteImageRepository {
    override fun uploadImage(bitmap: Bitmap, id:String, callback:(Boolean, String?)->Unit) {
        val storageRef = storage.reference.child("image").child(id)

        val baos =ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = storageRef.putBytes(data)
        uploadTask.addOnSuccessListener { _->
            storageRef.downloadUrl.addOnSuccessListener {
                callback(true, it.toString())
            }.addOnFailureListener {
                callback(false,it.message)
            }
        }.addOnFailureListener{
            callback(false,it.message)
        }
    }

    override fun deleteImage(id: String, callback: (Boolean) -> Unit) {
        storage.reference.child("image").child(id).delete().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
}