package com.noreplypratap.data.repository

import com.noreplypratap.data.mappers.toNewsArticle
import com.noreplypratap.data.mappers.toNewsArticleEntity
import com.noreplypratap.data.source.local.NewsArticleDao
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepositoryImpl(
    private val newsArticleDao: NewsArticleDao
): LocalRepository {
    override suspend fun createArticle(newsArticle: NewsArticle) {
        newsArticleDao.createArticle(newsArticle.toNewsArticleEntity())
    }

    override fun readArticles(): Flow<List<NewsArticle>> = newsArticleDao.readArticles().map {
        it.map { article ->
            article.toNewsArticle()
        }
    }
    override suspend fun updateArticle(newsArticle: NewsArticle) {
        newsArticleDao.updateArticle(newsArticle.toNewsArticleEntity())
    }

    override suspend fun deleteArticle(newsArticle: NewsArticle) {
        newsArticleDao.deleteArticle(newsArticle.toNewsArticleEntity())
    }

    override suspend fun deleteEverything() {
        newsArticleDao.deleteEverything()
    }
}