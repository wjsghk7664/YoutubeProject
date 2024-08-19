package com.example.youtubeproject.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Qualifier

@Qualifier
annotation class CacheChannel

@Qualifier
annotation class CacheVideo

@Qualifier
annotation class NextPageToken

@Qualifier
annotation class CacheLogin

@Module
@InstallIn(ViewModelComponent::class)
object SharedPreferencesModules {

    @CacheChannel
    @Provides
    fun provideCacheChannel(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("channel_pref", 0)
    }

    @CacheVideo
    @Provides
    fun provideCacheVideo(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("video_pref", 0)
    }

    @NextPageToken
    @Provides
    fun provideNextPageToken(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("token_pref", 0)
    }

    @CacheLogin
    @Provides
    fun provideCacheLogin(@ApplicationContext context: Context): SharedPreferences {
        val masterKey =
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            "Login_pref",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}