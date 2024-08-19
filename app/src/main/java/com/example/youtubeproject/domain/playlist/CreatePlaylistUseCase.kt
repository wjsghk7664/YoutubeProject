package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class CreatePlaylistUseCase @Inject constructor(
    //private val remotePlaylistRepository: RemotePlaylistRepository
) {
    suspend operator fun invoke(title: String): Playlist {
        //remotePlaylistRepository.create(title)
        return Playlist(1L, "")
    }
}