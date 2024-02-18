package com.noreplypratap.data.repository

import com.noreplypratap.data.mappers.toNewsArticle
import com.noreplypratap.data.source.remote.NewsApiService
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.repository.RemoteRepository
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val newsService: NewsApiService
) : RemoteRepository {
    override suspend fun getArticles(
        countryCode: String,
        category: String,
        q: String
    ): List<NewsArticle> {
        val res = newsService.getArticles(countryCode, category, q)
        val list = res.body()?.articles?.map {
            it.toNewsArticle()
        }
        return list?:emptyList()
    }

    override suspend fun searchArticles(queryString: String): List<NewsArticle> {
        val res = newsService.searchArticles(queryString)
        val list = res.body()?.articles?.map {
            it.toNewsArticle()
        }
        return list?:emptyList()
    }

}