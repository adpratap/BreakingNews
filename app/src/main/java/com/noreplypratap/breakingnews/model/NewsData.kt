package com.noreplypratap.breakingnews.model

data class NewsData(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)