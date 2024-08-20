package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class SavePlaylistUseCase @Inject constructor(
    private val remotePlaylistRepository: RemoteUserPlayListRepository
) {
    operator fun invoke(playlist: Playlist, callback: (Boolean) -> (Unit)) {
        remotePlaylistRepository.DeletePlayLists(playlist.id) { isDeleted ->
            if(isDeleted) {
                remotePlaylistRepository.AddPlayList(playlist.id, playlist, callback)
            } else {
                callback(false)
            }
        }
    }
}