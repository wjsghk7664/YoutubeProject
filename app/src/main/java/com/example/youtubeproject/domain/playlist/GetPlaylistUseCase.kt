package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    //private val remotePlaylistRepository: RemotePlaylistRepository
) {
    suspend operator fun invoke(): List<Playlist> {
        //TODO
        //remotePlaylistRepository.getPlaylist(userId)
        return listOf()
    }
}