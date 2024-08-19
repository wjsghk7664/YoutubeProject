package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    //private val remotePlaylistRepository: RemotePlaylistRepository
) {
    suspend operator fun invoke(playlist: Playlist) {
        //remotePlaylistRepository.delete(playlist)
    }
}