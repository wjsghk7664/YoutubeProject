package com.example.youtubeproject.domain.playlist

import android.util.Log
import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import javax.inject.Inject

class SavePlaylistUseCase @Inject constructor(
    private val remotePlaylistRepository: RemoteUserPlayListRepository
) {
    operator fun invoke(userId: String, initial: Playlist, playlist: Playlist, callback: (Boolean) -> (Unit)) {
        remotePlaylistRepository.deletePlayList(userId, initial) { isDeleted ->
            Log.d("SavePlaylistUseCase", "isDeleted: $isDeleted")
            if(isDeleted) {
                remotePlaylistRepository.AddPlayList(userId, playlist, callback)
            } else {
                callback(false)
            }
        }
    }
}