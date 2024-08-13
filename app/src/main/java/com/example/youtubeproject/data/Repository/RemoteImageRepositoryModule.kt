package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteImageRepositoryModule {
    @Binds
    abstract fun bindRemoteImageRepository(remoteImageRepositoryImpl: RemoteImageRepositoryImpl):RemoteImageRepository
}