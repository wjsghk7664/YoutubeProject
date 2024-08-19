package com.example.youtubeproject.presentation.uistate

sealed class SignUpUiState {
    data object Success: SignUpUiState()
    data class Failure(val reason:String): SignUpUiState()
    data class FailureRegister(val reason: String): SignUpUiState()
    data object Loading: SignUpUiState()
    data object Init: SignUpUiState()
}