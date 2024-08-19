package com.example.youtubeproject.domain

import com.example.youtubeproject.data.model.CategoryVideoModel
import com.example.youtubeproject.data.remote.youtube.CategoryVideoResult
import javax.inject.Inject

class CategoryVideoUseCase @Inject constructor(
    private val categoryVideoResult: CategoryVideoResult
) {
    suspend operator fun invoke(videoCategoryId: String): CategoryVideoModel {
        return categoryVideoResult.getCategoryVideo(videoCategoryId = videoCategoryId)
    }
}
