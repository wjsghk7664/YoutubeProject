package com.example.youtubeproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CacheChannelDao {
    @Query("SELECT * FROM cachechannel_table WHERE category = :category LIMIT 1")
    suspend fun getCacheChannel(category:String): CacheChannel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheChannel(cacheChannel: CacheChannel)

    @Query("DELETE FROM cachechannel_table")
    suspend fun deleteAllCacheChannel()

}