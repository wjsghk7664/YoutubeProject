package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class RemoteUserDataRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    RemoteUserDataRepository {
    override fun CheckDupId(id:String, callback:(Boolean,Int) -> Unit){
        var result:Boolean
        db.collection("User").document(id).get().addOnSuccessListener { document->
            if(document.exists()){
                callback(true,0)
            }else{
                callback(false,0)
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

    override fun Login(id: String, password: String, callback: (User?) -> Unit) {
        db.collection("User").document(id).get().addOnSuccessListener { document->
            if(document != null){
                val user = document.toObject(User::class.java)
                if(user!!.password ==password){
                    callback(user)
                }else{
                    callback(null)
                }
            }
        }.addOnFailureListener { _->
            callback(null)
        }
    }

}