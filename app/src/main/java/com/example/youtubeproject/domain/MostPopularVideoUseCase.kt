package com.example.youtubeproject.domain.usecase

import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.remote.youtube.MostPopularResult
import javax.inject.Inject

class GetPopularVideosUseCase @Inject constructor(
    private val mostPopularResult: MostPopularResult
) {
    suspend operator fun invoke(): CategoryVideoModel {
        return mostPopularResult.getPopular()
    }
}
