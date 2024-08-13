package com.example.youtubeproject.presentation

import com.example.youtubeproject.data.model.User

sealed class LoginUiState {
    data class Success(val user: User):LoginUiState()
    object FailureCache:LoginUiState()
    object Failure:LoginUiState()
    object Loading:LoginUiState()
    object Init:LoginUiState()
}