package com.example.youtubeproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.youtubeproject.data.model.TypeConverter

@Database(entities = [CacheChannel::class, CacheVideo::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class CacheDataBase: RoomDatabase() {
    abstract fun cacheChannelDao():CacheChannelDao
    abstract fun cacheVideoDao():CacheVideoDao
    abstract fun cacheLoginDao():CacheLoginDao
}