package com.example.youtubeproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheLoginDao {
    @Query("SELECT * FROM cachelogin_table LIMIT 1")
    suspend fun getLoginCache(): CacheLogin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoginCache(cacheLogin: CacheLogin)

    @Query("DELETE FROM cachelogin_table")
    suspend fun deleteLoginCache()
}