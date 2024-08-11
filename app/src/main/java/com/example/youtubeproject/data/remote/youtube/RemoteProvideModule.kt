package com.example.youtubeproject.data.remote.youtube

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RemoteProvideModule {
    private const val BASE_URL = "https://www.googleapis.com/youtube/"

    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideOkHttpClient(authorizationInterceptor: AuthorizationInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    fun provideMostPopularResult(retrofit: Retrofit): MostPopularResult {
        return retrofit.create(MostPopularResult::class.java)
    }

    @Provides
    fun provideSearchResult(retrofit: Retrofit): SearchResult {
        return retrofit.create(SearchResult::class.java)
    }

    @Provides
    fun provideCategoryChannelResult(retrofit: Retrofit): CategoryChannelResult {
        return retrofit.create(CategoryChannelResult::class.java)
    }

    @Provides
    fun provideCategoryVideoResult(retrofit: Retrofit): CategoryVideoResult {
        return retrofit.create(CategoryVideoResult::class.java)
    }
}