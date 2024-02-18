package com.noreplypratap.data.model.remote

data class NewsArticleDTO(
    var id : Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceDTO?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)