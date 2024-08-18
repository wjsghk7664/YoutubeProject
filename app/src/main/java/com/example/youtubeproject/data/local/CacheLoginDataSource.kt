package com.example.youtubeproject.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import javax.inject.Inject

class CacheLoginDataSource @Inject constructor(@CacheLogin private val sharedPreferencesLogin: SharedPreferences) {

    fun saveLoginData(id:String, password:String):Boolean{
        return sharedPreferencesLogin.edit().putString("id",id).putString("password",password).commit()
    }

    fun getLoginData():Pair<String?,String?>{
        val id = sharedPreferencesLogin.getString("id",null)
        val password = sharedPreferencesLogin.getString("password",null)
        return Pair(id,password)
    }

    fun deleteLoginData():Boolean{
        return sharedPreferencesLogin.edit().clear().commit()
    }
}