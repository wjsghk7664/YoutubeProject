package com.example.youtubeproject.domain.playlist

import com.example.youtubeproject.data.Repository.RemoteUserPlayListRepository
import com.example.youtubeproject.data.model.Playlist
import com.example.youtubeproject.data.model.UserPlayLists
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val remotePlaylistRepository: RemoteUserPlayListRepository
) {
    operator fun invoke(userId: String, callback: (UserPlayLists?) -> (Unit)) {
        remotePlaylistRepository.getAllPlayLists(userId, callback)
    }
}