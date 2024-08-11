package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheLoginDataRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCacheLoginDataRepository(cacheUserDataRepositoryImpl: CacheLoginDataRepositoryImpl):CacheLoginDataRepository
}