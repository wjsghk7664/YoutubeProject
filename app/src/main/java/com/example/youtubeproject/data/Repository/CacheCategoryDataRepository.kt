package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel

interface CacheCategoryDataRepository {
    suspend fun getCategoryVideoResult(category: String,page: String?):Result<CategoryVideoModel>
    suspend fun getCategoryChannelResult(category: String, page: String?):Result<CategoryChannelModel>
    suspend fun insertCategoryVideo(categoryVideoModel: CategoryVideoModel, category: String):Result<Boolean>
    suspend fun insertCategoryChannel(categoryChannelModel: CategoryChannelModel, category: String):Result<Boolean>
    suspend fun deleteCategoryVideo(category: String):Result<Boolean>
    suspend fun deleteCategoryChannel(category: String):Result<Boolean>
}