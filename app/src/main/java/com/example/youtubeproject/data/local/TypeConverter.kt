package com.example.youtubeproject.data.local

import android.provider.MediaStore.Video
import androidx.lifecycle.ViewModel
import androidx.room.TypeConverter
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.VideoModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromCategoryChannelModel(categoryChannelModel: CategoryChannelModel?): String?{
        return categoryChannelModel?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCategoryChannelModel(categoryChannelModelString: String?): CategoryChannelModel?{
        return categoryChannelModelString?.let {
            val type = object :TypeToken<CategoryChannelModel>(){}.type
            Gson().fromJson(it,type)
        }
    }

    @TypeConverter
    fun fromCategoryVideoModel(categoryVideoModel: CategoryVideoModel?): String?{
        return categoryVideoModel?.let{ Gson().toJson(it)}
    }

    @TypeConverter
    fun toCategoryVideoModel(categoryVideoModelString: String?): CategoryVideoModel?{
        return categoryVideoModelString?.let {
            val type = object : TypeToken<CategoryVideoModel>(){}.type
            Gson().fromJson(it,type)
        }
    }

    @TypeConverter
    fun fromVideoModel(videoModel: VideoModel?): String?{
        return videoModel.let{ Gson().toJson(it) }
    }

    @TypeConverter
    fun toVideoModel(videoModelString: String?): ViewModel?{
        return videoModelString?.let {
            val type = object :TypeToken<VideoModel>(){}.type
            Gson().fromJson(it,type)
        }
    }
}