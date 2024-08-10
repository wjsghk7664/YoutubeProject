package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.model.SearchResultModel
import com.example.youtubeproject.data.remote.CategoryChannelResult
import com.example.youtubeproject.data.remote.CategoryVideoResult
import com.example.youtubeproject.data.remote.MostPopularResult
import com.example.youtubeproject.data.remote.SearchResult
import javax.inject.Inject

class GetRemoteDataRepositoryImpl @Inject constructor(
    private val mostPopularResult: MostPopularResult,
    private val categoryChannelResult: CategoryChannelResult,
    private val categoryVideoResult: CategoryVideoResult,
    private val searchResult: SearchResult
): GetRemoteDataRepository {
    override suspend fun getPopularResult(page: String?): Result<CategoryVideoModel> {
        return runCatching {
            mostPopularResult.getPopular(pageToken = page)
        }
    }

    override suspend fun getSearchResult(query: String, page: String?, category: String?): Result<SearchResultModel> {
        return runCatching {
            searchResult.getSearchResult(q = query, pageToken = page, videoCategoryId = category)
        }
    }

    override suspend fun getCategoryVideoResult(
        category: String,
        page: String?
    ): Result<CategoryVideoModel> {
        return runCatching {
            categoryVideoResult.getCategoryVideo(videoCategoryId = category, pageToken = page)
        }
    }

    override suspend fun getCategoryChannelResult(
        category: String,
        page: String?
    ): Result<CategoryChannelModel> {
        return runCatching {
            categoryChannelResult.getCategoryChannel(id = category, pageToken = page)
        }
    }

}