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

    fun getPlaylistDetail(userId: String, playlistId: String) {
        runCatching {
            getPlaylistUseCase.invoke(userId) { result ->
                if(result != null) {
                    val detail = result.playLists.first { it.id == playlistId }
                    _uiState.value = PlaylistUiState.GetPlaylistDetailSuccess(detail)
                } else {
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            _uiState.value = PlaylistUiState.Failure
        }
    }

    fun getPlaylists(userId: String) {
        runCatching {
            getPlaylistUseCase.invoke(userId) { playlists ->
                if(playlists != null) {
                    _uiState.value = PlaylistUiState.GetPlaylistsSuccess(playlists.playLists)
                } else {
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            _uiState.value = PlaylistUiState.Failure
        }
    }
    fun savePlaylist(playlist: Playlist) {
        runCatching {
            savePlaylistUseCase.invoke(playlist) { isSuccess ->
                if(isSuccess) {
                    _uiState.value = PlaylistUiState.SavePlaylistSuccess
                } else {
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            _uiState.value = PlaylistUiState.Failure
        }
    }
    fun createPlaylist(playlist: Playlist) {
        runCatching {
            addPlaylistUseCase.invoke(playlist) { isSuccess ->
                if(isSuccess) {
                    _uiState.value = PlaylistUiState.CreatePlaylistSuccess(playlist)
                } else {
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            _uiState.value = PlaylistUiState.Failure
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        runCatching {
            deletePlaylistUseCase.invoke(playlist) { isDeleted ->
                if(isDeleted) {
                    _uiState.value = PlaylistUiState.DeletePlaylistSuccess
                } else {
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            _uiState.value = PlaylistUiState.Failure
        }
    }
}