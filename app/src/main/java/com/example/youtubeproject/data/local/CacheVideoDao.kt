package com.example.youtubeproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.youtubeproject.data.model.CacheVideo


@Dao
interface CacheVideoDao {
    @Query("SELECT * FROM cachevideo_table WHERE category = :category LIMIT 1")
    suspend fun getCacheVideo(category:String): CacheVideo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoChannel(cacheVideo: CacheVideo)

    @Query("DELETE FROM cachevideo_table")
    suspend fun deleteAllCacheVideo()
}