package com.example.youtubeproject.presentation.uistate

import com.example.youtubeproject.data.model.SearchResultModel

sealed class SearchUiState {
    data class Success(val searchResultModel: SearchResultModel): SearchUiState()
    data object Loading: SearchUiState()
    data object LoadingMore: SearchUiState()
    data object Empty: SearchUiState()
    data object Failure: SearchUiState()
    data object Init: SearchUiState()
}