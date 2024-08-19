package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.domain.playlist.CreatePlaylistUseCase
import com.example.youtubeproject.domain.playlist.GetPlaylistUseCase
import com.example.youtubeproject.domain.playlist.SavePlaylistUseCase
import com.example.youtubeproject.presentation.uistate.LoginUiState
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val savePlaylistUseCase: SavePlaylistUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun getPlaylists() {
        viewModelScope.launch {
            runCatching {
                getPlaylistUseCase.invoke()
            }.onFailure {
                _uiState.emit(PlaylistUiState.Failure)
            }.onSuccess {
                _uiState.emit(PlaylistUiState.GetPlaylistsSuccess(it))
            }
        }
    }
    fun savePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            runCatching {
                savePlaylistUseCase.invoke(playlist)
            }.onFailure {
                _uiState.emit(PlaylistUiState.Failure)
            }.onSuccess {
                _uiState.emit(PlaylistUiState.SavePlaylistSuccess)
            }
        }
    }
    fun createPlaylist(title: String) {
        viewModelScope.launch {
            runCatching {
                createPlaylistUseCase.invoke(title)
            }.onFailure {
                _uiState.emit(PlaylistUiState.Failure)
            }.onSuccess {
                _uiState.emit(PlaylistUiState.CreatePlaylistSuccess)
            }
        }
    }
}