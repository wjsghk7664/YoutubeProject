package com.example.youtubeproject.data.model

import androidx.room.TypeConverter
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
}