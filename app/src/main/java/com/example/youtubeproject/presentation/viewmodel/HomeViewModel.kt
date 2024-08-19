package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.domain.CategoryVideoUseCase
import com.example.youtubeproject.domain.ChannelCategoryUseCase
import com.example.youtubeproject.domain.usecase.MostPopularVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
//
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mostPopularVideoUseCase: MostPopularVideoUseCase,
    private val categoryVideoUseCase: CategoryVideoUseCase,
) : ViewModel() {

    private val _popularVideos = MutableLiveData<CategoryVideoModel>()
    val popularVideos: LiveData<CategoryVideoModel> get() = _popularVideos

    private val _categoryVideos = MutableLiveData<CategoryVideoModel>()
    val categoryVideos: LiveData<CategoryVideoModel> get() = _categoryVideos

    private val _categoryChannels = MutableLiveData<CategoryChannelModel>()
    val categoryChannels: LiveData<CategoryChannelModel> get() = _categoryChannels

    fun loadPopularVideos() {
        viewModelScope.launch {
            try {
                val popularVideos = mostPopularVideoUseCase()
                _popularVideos.value = popularVideos
            } catch (e: Exception) {
                Log.e("에러 popularvideo", "Error", e)
            }
        }
    }

    fun loadCategoryVideos(categoryId: String) {
        viewModelScope.launch {
            try {
                val categoryVideos = categoryVideoUseCase(categoryId)
                _categoryVideos.value = categoryVideos
            } catch (e: Exception) {
                Log.e("에러 categoryVideo", "Error", e)
            }
        }
    }













}
