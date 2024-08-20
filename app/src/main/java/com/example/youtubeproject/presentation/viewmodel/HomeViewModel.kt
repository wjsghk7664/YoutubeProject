package com.example.youtubeproject.presentation.viewmodel

import VideoItem
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.Repository.RemoteApiDataRepository
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteApiDataRepository: RemoteApiDataRepository // 레포지토리 주입
) : ViewModel() {



    private val _categoryVideos = MutableLiveData<CategoryVideoModel>()
    val categoryVideos: LiveData<CategoryVideoModel> get() = _categoryVideos

    private val _categoryChannels = MutableLiveData<CategoryChannelModel>()
    val categoryChannels: LiveData<CategoryChannelModel> get() = _categoryChannels

    private val _popularVideos = MutableLiveData<List<VideoItem>>()
    val popularVideos: LiveData<List<VideoItem>> get() = _popularVideos

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
                            description = videoResponse.snippet?.title ?: ""
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
                    _categoryVideos.value = categoryVideos

                    val channelIds = categoryVideos.items.mapNotNull { it.snippet?.channelId }.distinct()

                    if (channelIds.isNotEmpty()) {
                        loadCategoryChannels(channelIds)
                    } else {
                    }
                }.onFailure { e ->
                    Log.e("에l", "비디오 로딩 실패", e)
                }
            } catch (e: Exception) {
                Log.e("에러", "에러", e)
            }
        }
    }

    private fun loadCategoryChannels(channelIds: List<String>) {
        viewModelScope.launch {
            try {

                // 여러 채널 ID를 쉼표로 구분하여 API 요청
                val result = remoteApiDataRepository.getCategoryChannelResult(channelIds.joinToString(","), null)

                result.onSuccess { categoryChannels ->
                    _categoryChannels.value = categoryChannels
                }.onFailure { e ->
                    Log.e("에l", "케타고리 로딩 실패", e)
                }
            } catch (e: Exception) {
                Log.e("에러", "에러", e)
            }
        }
    }
}
