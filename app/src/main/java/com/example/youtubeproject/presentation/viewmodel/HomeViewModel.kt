package com.example.youtubeproject.presentation.viewmodel

import VideoItem
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.Repository.RemoteApiDataRepository
import com.example.youtubeproject.presentation.ui.ChannelCategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteApiDataRepository: RemoteApiDataRepository
) : ViewModel() {

    private val _popularVideos = MutableLiveData<List<VideoItem>>()
    val popularVideos: LiveData<List<VideoItem>> get() = _popularVideos

    private val _categoryVideos = MutableLiveData<List<VideoItem>>()
    val categoryVideos: LiveData<List<VideoItem>> get() = _categoryVideos

    private val _categoryChannels = MutableLiveData<List<ChannelCategoryItem>>()
    val categoryChannels: LiveData<List<ChannelCategoryItem>> get() = _categoryChannels

    fun loadPopularVideos() {
        viewModelScope.launch {
            try {
                val result = remoteApiDataRepository.getPopularResult(null)
                result.onSuccess { popularVideos ->
                    val videoItems = popularVideos.items.map { videoResponse ->
                        val channelId = videoResponse.snippet?.channelId ?: ""
                        val channelThumbnailUrl = remoteApiDataRepository.getChannelThumbnail(channelId).getOrNull()

                        VideoItem(
                            mainImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                            profileImageUrl = channelThumbnailUrl,
                            description = videoResponse.snippet?.description ?: ""
                            ,
                            title = videoResponse.snippet?.title ?: ""

                        )
                    }
                    _popularVideos.value = videoItems
                }.onFailure { e ->
                    Log.e("에러 popularvideo", "Error", e)
                }
            } catch (e: Exception) {
                Log.e("에러 popularvideo", "Error", e)
            }
        }
    }

    fun loadCategoryVideos(categoryId: String) {
        viewModelScope.launch {
            try {
                val result = remoteApiDataRepository.getCategoryVideoResult(categoryId, null)
                result.onSuccess { categoryVideos ->
                    val videoItems = categoryVideos.items.map { videoResponse ->
                        VideoItem(
                            mainImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                            profileImageUrl = videoResponse.snippet?.thumbnails?.high?.url,
                            description = videoResponse.snippet?.description ?: "",
                            title = videoResponse.snippet?.title ?: ""
                        )
                    }
                    _categoryVideos.value = videoItems

                    val channelIds = categoryVideos.items.mapNotNull { it.snippet?.channelId }.distinct()
                    if (channelIds.isNotEmpty()) {
                        loadCategoryChannels(channelIds)
                    } else {
                    }
                }.onFailure { e ->
                    Log.e("오류", "비디오 로딩 실패23 - category ID: $categoryId", e)
                }
            } catch (e: Exception) {
                Log.e("오류", "오류발생 - category ID: $categoryId", e)
            }
        }
    }

    private fun loadCategoryChannels(channelIds: List<String>) {
        viewModelScope.launch {
            try {
                val result = remoteApiDataRepository.getCategoryChannelResult(channelIds.joinToString(","), null)
                result.onSuccess { categoryChannels ->
                    val channelItems = categoryChannels.items.mapNotNull { channelResponse ->
                        ChannelCategoryItem(
                            imageResId = channelResponse.snippet?.thumbnails?.high?.url,
                            channelName = channelResponse.snippet?.title ?: ""
                        )
                    }
                    _categoryChannels.value = channelItems
                }.onFailure { e ->
                    Log.e("오류", "비디오 로딩 실패123 ", e)
                }
            } catch (e: Exception) {
                Log.e("오류", "오류발생", e)
            }
        }
    }
}
