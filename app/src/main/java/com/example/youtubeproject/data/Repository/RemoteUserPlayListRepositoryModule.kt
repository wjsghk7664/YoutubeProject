package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteUserPlayListRepositoryModule {
    @Binds
    abstract fun bindRemoteUserPlayListRepository(remoteUserPlayListRepositoryImpl: RemoteUserPlayListRepositoryImpl):RemoteUserPlayListRepository
}