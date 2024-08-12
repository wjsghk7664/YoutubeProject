package com.example.youtubeproject.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object CacheDatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CacheDataBase {
        return Room.databaseBuilder(
            context,
            CacheDataBase::class.java,
            "cache_database"
        ).build()
    }

    @Provides
    fun provideChannelDao(dataBase: CacheDataBase): CacheChannelDao {
        return dataBase.cacheChannelDao()
    }

    @Provides
    fun provideVideoDao(dataBase: CacheDataBase): CacheVideoDao{
        return dataBase.cacheVideoDao()
    }

    @Provides
    fun provideLoginDao(dataBase: CacheDataBase): CacheLoginDao{
        return dataBase.cacheLoginDao()
    }

    @Provides
    fun provideCacheLikeListDao(dataBase: CacheDataBase): CacheLikeListDao{
        return dataBase.cacheLikeListDao()
    }
}