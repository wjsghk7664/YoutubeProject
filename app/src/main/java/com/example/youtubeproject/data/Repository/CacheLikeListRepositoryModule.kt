package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CacheLikeListRepositoryModule {

    @Binds
    abstract fun bindCacheLikeListRepository(cacheLikeListRepositoryImpl: CacheLikeListRepositoryImpl):CacheLikeListRepository
}