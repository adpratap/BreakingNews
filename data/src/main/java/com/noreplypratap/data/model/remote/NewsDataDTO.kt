package com.noreplypratap.data.model.remote

data class NewsDataDTO(
    val articles: MutableList<NewsArticleDTO>,
    val status: String,
    val totalResults: Int
)