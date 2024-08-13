package com.example.youtubeproject.data.Repository

interface CacheLoginDataRepository {
    fun saveLoginData(id:String, password:String):Result<Boolean>
    fun getLoginData():Result<Pair<String?,String?>>
    fun deleteLoginData():Result<Boolean>
}