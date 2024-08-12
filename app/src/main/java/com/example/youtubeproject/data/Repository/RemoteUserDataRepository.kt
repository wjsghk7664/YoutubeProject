package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.User

interface RemoteUserDataRepository {
    fun CheckDupId(id:String, callback:(Boolean,Int) ->Unit)
    fun AddOrModifyUserData(user: User, callback: (Boolean) -> Unit)
    fun DeleteUserData(id:String, callback: (Boolean) -> Unit)
    fun Check(id:String, password:String, callback: (User?) -> Unit)
}