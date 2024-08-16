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

    private val _selectCategory = MutableStateFlow<String?>(null)
    val selectCategory: StateFlow<String?> = _selectCategory.asStateFlow()

    fun searchVideo(query: String, page: String?, category: String?) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading

            searchUseCase(query, page, category) {
                if (it != null) {
                    if (it.items?.isEmpty() == true) _uiState.value = SearchUiState.Empty
                    else {
                        if (page == null) _searchResult.value = mutableListOf()

                        _uiState.value = SearchUiState.Success(it)

                        val currentResult = _searchResult.value.toMutableList()
                        currentResult.addAll(it.items!!)
                        _searchResult.value = currentResult
                    }
                } else {
                    _uiState.value = SearchUiState.Failure
                }
            }
        }
    }
}