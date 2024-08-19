package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.LikeList
import com.example.youtubeproject.data.model.VideoModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RemoteUserLikeListRepositoryImpl @Inject constructor(private val db:FirebaseFirestore):RemoteUserLikeListRepository {
    override fun AddLike(id:String,videoModel: VideoModel, callback: (Boolean) -> Unit) {
        db.collection("LikeList").document(id).update("likeList",FieldValue.arrayUnion(videoModel))
            .addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
    }


    override fun DeleteLike(id: String, videoModel: VideoModel, callback: (Boolean) -> Unit) {
        db.collection("LikeList").document(id).update("likeList", FieldValue.arrayRemove(videoModel))
            .addOnSuccessListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
    }

    override fun createList(id: String, callback: (Boolean) -> Unit) {
        db.collection("LikeList").document(id).set(LikeList()).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    override fun deleteList(id: String, callback: (Boolean) -> Unit) {
        db.collection("LikeList").document(id).delete().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    override fun getList(id: String, callback: (LikeList?) -> Unit) {
        db.collection("LikeList").document(id).get().addOnSuccessListener { document->
            if(document!=null&&document.exists()){
                val list = document.toObject(LikeList::class.java)
                callback(list)
            }else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

}