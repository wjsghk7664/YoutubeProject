package com.example.youtubeproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.youtubeproject.data.model.CacheLogin

@Dao
interface CacheLoginDao {
    @Query("SELECT * FROM cachelogin_table LIMIT 1")
    fun getLoginCache(): CacheLogin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginCache(cacheLogin: CacheLogin)

    @Query("DELETE FROM cachelogin_table")
    fun deleteLoginCache()
}