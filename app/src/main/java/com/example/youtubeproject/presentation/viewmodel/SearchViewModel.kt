package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.SearchResponse
import com.example.youtubeproject.domain.SearchUseCase
import com.example.youtubeproject.presentation.uistate.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchResult = MutableStateFlow<MutableList<SearchResponse>>(mutableListOf())
    val searchResult: StateFlow<MutableList<SearchResponse>> = _searchResult.asStateFlow()

    private val _isLoadMore = MutableStateFlow(false)
    val isLoadMore: StateFlow<Boolean> = _isLoadMore.asStateFlow()

    fun searchVideo(query: String, page: String?, category: String?) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            searchUseCase(query, page, category) {
                if (it?.items != null) {
                    if (it.items.isEmpty()) _uiState.value = SearchUiState.Empty
                    else {
                        _uiState.value = SearchUiState.Success(it)
                        _searchResult.value = it.items.toMutableList()
                    }
                } else {
                    _uiState.value = SearchUiState.Failure
                }
            }
        }
    }

    fun searchResultMore(query: String, page: String, category: String?) {
        viewModelScope.launch {
            _isLoadMore.value = true

            searchUseCase(query, page, category) {
                if (it?.items != null) {
                    if (it.items.isEmpty()) _uiState.value = SearchUiState.Empty
                    else {
                        _uiState.value = SearchUiState.Success(it)

                        val currentList = _searchResult.value.toMutableList()
                        currentList.addAll(it.items)

                        _searchResult.value = currentList
                    }

                    _isLoadMore.value = false
                } else {
                    _uiState.value = SearchUiState.Failure
                }
            }
        }
    }
}