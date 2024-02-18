package com.noreplypratap.domain.model

data class NewsArticle(
    var id : Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: String?
)