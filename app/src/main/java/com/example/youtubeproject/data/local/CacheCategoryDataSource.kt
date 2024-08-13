package com.example.youtubeproject.data.local

import android.content.SharedPreferences
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryChannelResponse
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.CategoryVideoResponse
import com.google.gson.Gson
import javax.inject.Inject

class CacheCategoryDataSource @Inject constructor(
    @CacheVideo private val sharedPreferencesVideo: SharedPreferences,
    @CacheChannel private val sharedPreferencesChannel: SharedPreferences,
    @NextPageToken private val sharedPreferencesNextPageToken: SharedPreferences
) {

    val gson = Gson()

    fun addVideo(category: String, categoryVideoModel: CategoryVideoModel): Boolean {
        val videoEditor = sharedPreferencesVideo.edit()
        for (i in categoryVideoModel.items) {
            val key = category + "_" + i.id
            val json = gson.toJson(i)
            videoEditor.putString(key, json)
        }
        val tokenEidtor = sharedPreferencesNextPageToken.edit()
        tokenEidtor.putString(category + "_video", categoryVideoModel.nextPageToken)
        return videoEditor.commit() && tokenEidtor.commit()
    }

    fun addChannel(category: String, categoryChannelModel: CategoryChannelModel): Boolean {
        val channelEditor = sharedPreferencesChannel.edit()
        for (i in categoryChannelModel.items) {
            val key = category + "_" + i.id
            val json = gson.toJson(i)
            channelEditor.putString(key, json)
        }
        val tokenEditor = sharedPreferencesNextPageToken.edit()
        tokenEditor.putString(category + "_channel", categoryChannelModel.nextPageToken)
        return channelEditor.commit() && tokenEditor.commit()
    }

    fun getVideo(category: String): CategoryVideoModel {
        val allEntries = sharedPreferencesVideo.all

        val items = ArrayList<CategoryVideoResponse>()

        for ((k, v) in allEntries) {
            if (k.startsWith(category + "_")) {
                items += gson.fromJson(v as String, CategoryVideoResponse::class.java)
            }
        }

        val token = sharedPreferencesNextPageToken.getString(category + "_video", "")
        return CategoryVideoModel(token, items as List<CategoryVideoResponse>)
    }

    fun getChannel(category: String): CategoryChannelModel {
        val allEntries = sharedPreferencesChannel.all

        val items = ArrayList<CategoryChannelResponse>()

        for ((k, v) in allEntries) {
            if (k.startsWith(category + "_")) {
                items += gson.fromJson(v as String, CategoryChannelResponse::class.java)
            }
        }

        val token = sharedPreferencesNextPageToken.getString(category + "_channel", "")
        return CategoryChannelModel(token, items as List<CategoryChannelResponse>)
    }

    fun deleteVideo(category: String): Boolean {
        return sharedPreferencesVideo.edit().clear()
            .commit() && sharedPreferencesNextPageToken.edit().remove(category + "_video").commit()
    }

    fun deleteChannel(category: String):Boolean {
        return sharedPreferencesChannel.edit().clear()
            .commit() && sharedPreferencesNextPageToken.edit().remove(category+"_channel").commit()
    }


}