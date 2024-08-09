package com.example.youtubeproject.data.model

data class SearchResultModel(
    val nextPageToken: String?,
    val items: List<SearchResponse>?
)