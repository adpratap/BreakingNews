package com.noreplypratap.domain.repository

import com.noreplypratap.domain.model.NewsArticle

interface RemoteRepository {
    suspend fun getArticles(
        countryCode: String,
        category: String = "",
        q: String = ""
    ): List<NewsArticle>

    suspend fun searchArticles(
        queryString: String
    ): List<NewsArticle>

}



