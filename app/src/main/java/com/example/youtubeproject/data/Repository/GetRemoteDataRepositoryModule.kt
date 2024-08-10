package com.example.youtubeproject.data.Repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier


@Module
@InstallIn(ViewModelComponent::class)
abstract class GetDataRepositoryModule {

    @Binds
    abstract fun bindRemoteDataRepository(getRemoteDataRepositoryImpl: GetRemoteDataRepositoryImpl): GetRemoteDataRepository

    @Binds
    abstract fun bindLocalDataRepository(getLocalDataRepositoryImpl: GetLocalDataRepositoryImpl): GetLocalDataRepository
}