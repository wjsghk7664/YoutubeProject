package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class DeletePlaylistUseCase @Inject constructor(
    private val remotePlaylistRepository: RemoteUserPlayListRepository
) {
    operator fun invoke(playlist: Playlist, callback: (Boolean) -> (Unit)) {
        remotePlaylistRepository.DeletePlayLists(playlist.id, callback)
    }
}