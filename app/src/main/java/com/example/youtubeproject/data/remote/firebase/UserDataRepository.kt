package com.example.youtubeproject.data.remote.firebase

import com.example.youtubeproject.data.model.User

interface UserDataRepository {
    fun CheckDupId(id:String, callback:(Boolean,Int) ->Unit)
    fun AddOrModifyUserData(user: User, callback: (Boolean) -> Unit)
    fun DeleteUserData(id:String, callback: (Boolean) -> Unit)
    fun Login(id:String, password:String, callback: (User?) -> Unit)
}