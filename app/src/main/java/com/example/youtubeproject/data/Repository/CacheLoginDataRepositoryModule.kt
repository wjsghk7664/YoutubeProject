package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class CacheLoginDataRepositoryModule {
    @Binds
    abstract fun bindCacheLoginDataRepository(cacheUserDataRepositoryImpl: CacheLoginDataRepositoryImpl):CacheLoginDataRepository
}