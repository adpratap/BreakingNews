package com.noreplypratap.breakingnews.repository

import com.noreplypratap.breakingnews.api.NewsService
import com.noreplypratap.breakingnews.db.NewsArticleDao
import com.noreplypratap.breakingnews.model.Article
import javax.inject.Inject


class Repository @Inject constructor(
    private val newsService: NewsService,
    private val newsArticleDao: NewsArticleDao
) {
    suspend fun getNewsData(countryCode: String, category: String, q: String) =
        newsService.loadNewsFormAPI(countryCode, category, q)

    suspend fun searchNewsData(queryString: String) = newsService.searchNews(queryString)

    suspend fun deleteFavNew(article: Article) = newsArticleDao.deleteFavNews(article)

    fun getArticleRoomDB() = newsArticleDao.getArticlesRoomDB()
    suspend fun saveArticles(articles: MutableList<Article>) = newsArticleDao.saveArticle(articles)
    suspend fun saveFavNews(article: Article) = newsArticleDao.saveFavNews(article)

}
