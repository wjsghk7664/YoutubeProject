package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import java.time.LocalDateTime
import javax.inject.Inject

class AddPlaylistUseCase @Inject constructor(
    private val remotePlaylistRepository: RemoteUserPlayListRepository
) {
    operator fun invoke(userId: String, playlist: Playlist, callback: (Boolean) -> (Unit)): Playlist {
        remotePlaylistRepository.AddPlayList(userId, playlist, callback)
        return playlist
    }
}