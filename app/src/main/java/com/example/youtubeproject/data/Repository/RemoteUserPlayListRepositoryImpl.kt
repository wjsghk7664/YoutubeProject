package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.UserPlayLists
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RemoteUserPlayListRepositoryImpl @Inject constructor(private val db:FirebaseFirestore):RemoteUserPlayListRepository {
    //유저의 플레이 리스트 목록 생성 ->회원가입에서 처리
    override fun CreatePlayLists(id: String, callback: (Boolean) -> Unit) {
        db.collection("PlayList").document(id).set(UserPlayLists()).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    //유저의 플레이 리스트 목록 제거 ->회원탈퇴에서 처리
    override fun DeletePlayLists(id: String, callback: (Boolean) -> Unit) {
        db.collection("PlayList").document(id).delete().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    //플레이 리스트 추가
    override fun AddPlayList(id: String, playlist: Playlist, callback: (Boolean) -> Unit) {
        db.collection("PlayList").document(id).update("playLists",FieldValue.arrayUnion(playlist)).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }
    //플레이 리스트 제거
    override fun deletePlayList(id: String, playlist: Playlist, callback: (Boolean) -> Unit) {
        db.collection("PlayList").document(id).update("playLists",FieldValue.arrayRemove(playlist)).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    override fun getAllPlayLists(id: String, callback: (UserPlayLists?) -> Unit) {
        db.collection("PlayList").document(id).get().addOnSuccessListener { document->
            if(document!=null&&document.exists()){
                val list = document.toObject(UserPlayLists::class.java)
                callback(list)
            }else{
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }
}