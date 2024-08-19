package com.example.youtubeproject.domain

import android.util.Log
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.remote.youtube.CategoryChannelResult
import javax.inject.Inject

class ChannelCategoryUseCase @Inject constructor(
    private val categoryChannelResult: CategoryChannelResult
) {
    suspend operator fun invoke(id: String): CategoryChannelModel {
        return categoryChannelResult.getCategoryChannel(id = id)
    }
}

