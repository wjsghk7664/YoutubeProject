package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.domain.playlist.AddPlaylistUseCase
import com.example.youtubeproject.domain.playlist.DeletePlaylistUseCase
import com.example.youtubeproject.domain.playlist.GetPlaylistUseCase
import com.example.youtubeproject.domain.playlist.SavePlaylistUseCase
import com.example.youtubeproject.presentation.uistate.PlaylistUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val savePlaylistUseCase: SavePlaylistUseCase,
    private val addPlaylistUseCase: AddPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PlaylistUiState>(PlaylistUiState.Init)
    val uiState = _uiState.asStateFlow()

    fun getPlaylistDetail(id: Long): Playlist {
        return Playlist(1L, "TITLE")
    }

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
                addPlaylistUseCase.invoke(title)
            }.onFailure {
                _uiState.emit(PlaylistUiState.Failure)
            }.onSuccess {
                _uiState.emit(PlaylistUiState.CreatePlaylistSuccess(it))
            }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            runCatching {
                deletePlaylistUseCase.invoke(playlist)
            }.onFailure {
                _uiState.emit(PlaylistUiState.Failure)
            }.onSuccess {
                _uiState.emit(PlaylistUiState.DeletePlaylistSuccess)
            }
        }
    }
}