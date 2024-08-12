package com.example.youtubeproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.youtubeproject.data.model.VideoModel


@Dao
interface CacheLikeListDao {
    @Query("SELECT * FROM cachelikelist_table")
    suspend fun getCacheLikeList(): List<VideoModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCacheLikeItem(videoModel: VideoModel)

    @Query("DELETE FROM cachelikelist_table WHERE id =:itemId")
    suspend fun deleteCacheLikeItem(itemId:String)

    @Query("DELETE FROM cachelikelist_table")
    suspend fun deleteCacheLikeList()
}