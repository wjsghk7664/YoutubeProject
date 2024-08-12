package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RemoteUserDataRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteUserDataRepository {
    override fun CheckDupId(id:String, callback:(Boolean,Int) -> Unit){
        db.collection("User").document(id).get().addOnSuccessListener { document->
            if(document.exists()){
                callback(false,0)
            }else{
                callback(true,0)
            }
        }.addOnFailureListener { exception->
            callback(false,1)
        }
    }

    override fun AddOrModifyUserData(user: User, callback: (Boolean) -> Unit) {
        db.collection("User").document(user.id).set(user).addOnSuccessListener {
            callback(true)
        }.addOnFailureListener {
            callback(false)
        }
    }

    override fun DeleteUserData(id: String, callback: (Boolean) -> Unit) {
        db.collection("User").document(id).delete().addOnSuccessListener {
            callback(true)
        }.addOnFailureListener { _->
            callback(false)
        }
    }

    //로그인은 usecase에서 콜백으로 들어온 값을 복사해서 마지막 로그인 시점을 갱신후 로컬과 파이어베이스에 저장
    override fun Check(id: String, password: String, callback: (User?) -> Unit) {
        db.collection("User").document(id).get().addOnSuccessListener { document->
            if(document.exists()){
                val user = document.toObject(User::class.java)
                if(user!!.password ==password){
                    callback(user)
                }else{
                    callback(null)
                }
            }else{
                callback(null)
            }
        }.addOnFailureListener { _->
            callback(null)
        }
    }

}