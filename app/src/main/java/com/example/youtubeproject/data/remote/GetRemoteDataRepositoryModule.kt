package com.example.youtubeproject.data.remote

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class GetRemoteDataRepositoryModule {
    @Binds
    abstract fun bindRemoteDataRepository(getRemoteDataRepositoryImpl: GetRemoteDataRepositoryImpl):GetRemoteDataRepository
}