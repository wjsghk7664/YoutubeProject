package com.example.youtubeproject.domain

import com.example.youtubeproject.data.Repository.RemoteApiDataRepositoryImpl
import com.example.youtubeproject.data.model.SearchResultModel
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val remoteApiDataRepositoryImpl: RemoteApiDataRepositoryImpl) {
    suspend operator fun invoke(query: String, page: String?, category: String?, callback: (SearchResultModel?) -> Unit) {
        remoteApiDataRepositoryImpl.getSearchResult(query, page, category)
            .onSuccess {
                callback(it)
            }.onFailure {
                callback(null)
            }
    }
}