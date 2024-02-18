package com.noreplypratap.domain.repository


import com.noreplypratap.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow


interface LocalRepository {
    suspend fun createArticle(newsArticle: NewsArticle)
    fun readArticles(): Flow<List<NewsArticle>>
    suspend fun updateArticle(newsArticle: NewsArticle)
    suspend fun deleteArticle(newsArticle: NewsArticle)
    suspend fun deleteEverything()

}