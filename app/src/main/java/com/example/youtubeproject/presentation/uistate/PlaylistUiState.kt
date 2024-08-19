package com.example.youtubeproject.presentation.uistate

import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.User

sealed class PlaylistUiState {
    data class GetPlaylistsSuccess(val playlists: List<Playlist>): PlaylistUiState()
    data class CreatePlaylistSuccess(val playlist: Playlist) : PlaylistUiState()

    data object SavePlaylistSuccess: PlaylistUiState()
    data object DeletePlaylistSuccess: PlaylistUiState()

    data object Failure: PlaylistUiState()
    data object Loading: PlaylistUiState()
    data object Init: PlaylistUiState()
}