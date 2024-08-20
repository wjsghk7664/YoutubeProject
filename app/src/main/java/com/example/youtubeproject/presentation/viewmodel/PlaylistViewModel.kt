package com.example.youtubeproject.presentation.viewmodel

import android.util.Log
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
import java.time.LocalDateTime
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
                    Log.d("PlaylistViewModel", "GetPlaylists: ${userId} success")
                    _uiState.value = PlaylistUiState.GetPlaylistsSuccess(playlists.playLists)
                } else {
                    Log.d("PlaylistViewModel", "GetPlaylists: ${userId} fail")
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            Log.d("PlaylistViewModel", "GetPlaylists: ${userId} onFailure")
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
    fun createPlaylist(userId: String, title: String) {
        runCatching {
            val newPlaylist = Playlist(
                LocalDateTime.now().toString(),
                title
            )
            addPlaylistUseCase.invoke(userId, newPlaylist) { isSuccess ->
                if(isSuccess) {
                    Log.d("PlaylistViewModel", "createPlaylist: ${newPlaylist.title} success")
                    _uiState.value = PlaylistUiState.CreatePlaylistSuccess(newPlaylist)
                } else {
                    Log.d("PlaylistViewModel", "createPlaylist: ${newPlaylist.title} fail")
                    _uiState.value = PlaylistUiState.Failure
                }
            }
        }.onFailure {
            Log.d("PlaylistViewModel", "createPlaylist onFailure")
            _uiState.value = PlaylistUiState.Failure
        }
    }

    fun deletePlaylist(userId: String, playlist: Playlist) {
        runCatching {
            deletePlaylistUseCase.invoke(userId, playlist) { isDeleted ->
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