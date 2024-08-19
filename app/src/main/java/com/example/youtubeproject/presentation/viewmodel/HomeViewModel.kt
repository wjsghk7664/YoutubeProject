package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.domain.usecase.GetPopularVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularVideosUseCase: GetPopularVideosUseCase
) : ViewModel() {

    private val _popularVideos = MutableLiveData<CategoryVideoModel>()
    val popularVideos: LiveData<CategoryVideoModel> get() = _popularVideos

    fun loadPopularVideos() {
        viewModelScope.launch {
            try {
                val popularVideos = getPopularVideosUseCase()
                _popularVideos.value = popularVideos
                Log.d("정상", " $popularVideos")
            } catch (e: Exception) {
                Log.e("에러", "Error", e)
            }
        }
    }

}
