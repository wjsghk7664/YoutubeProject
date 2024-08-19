package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.youtubeproject.domain.usecase.GetPopularVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPopularVideosUseCase: GetPopularVideosUseCase
) : ViewModel() {

}