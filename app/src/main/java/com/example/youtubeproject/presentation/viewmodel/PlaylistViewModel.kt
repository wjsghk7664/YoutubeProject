package com.example.youtubeproject.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.domain.playlist.GetPlaylistUseCase
import com.example.youtubeproject.domain.playlist.SavePlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val savePlaylistUseCase: SavePlaylistUseCase
) : ViewModel() {
    suspend fun getPlaylist() {
        getPlaylistUseCase.invoke()
    }
    suspend fun savePlaylist(playlist: Playlist) {
        savePlaylistUseCase.invoke(playlist)
    }
}