package com.example.youtubeproject.data.Repository

import com.example.youtubeproject.data.local.CacheCategoryDataSource
import com.example.youtubeproject.data.model.CategoryChannelModel
import com.example.youtubeproject.data.model.CategoryVideoModel
import javax.inject.Inject

class CacheCategoryDataRepositoryImpl @Inject constructor(
    private val cacheCategoryDataSource: CacheCategoryDataSource
):CacheCategoryDataRepository {
    override suspend fun getCategoryVideoResult(
        category: String,
        page: String?
    ): Result<CategoryVideoModel> {
        return runCatching {
            cacheCategoryDataSource.getVideo(category)
        }
    }

    override suspend fun getCategoryChannelResult(
        category: String,
        page: String?
    ): Result<CategoryChannelModel> {
        return runCatching {
            cacheCategoryDataSource.getChannel(category)
        }
    }

    override suspend fun insertCategoryVideo(
        categoryVideoModel: CategoryVideoModel,
        category: String
    ):Result<Boolean> {
        return runCatching {
            cacheCategoryDataSource.addVideo(category,categoryVideoModel)
        }
    }

    override suspend fun insertCategoryChannel(
        categoryChannelModel: CategoryChannelModel,
        category: String
    ):Result<Boolean> {
        return runCatching {
            cacheCategoryDataSource.addChannel(category,categoryChannelModel)
        }
    }

    override suspend fun deleteCategoryVideo(category: String):Result<Boolean> {
        return runCatching {
            cacheCategoryDataSource.deleteVideo(category)
        }
    }

    override suspend fun deleteCategoryChannel(category: String):Result<Boolean> {
        return runCatching {
            cacheCategoryDataSource.deleteChannel(category)
        }
    }
}