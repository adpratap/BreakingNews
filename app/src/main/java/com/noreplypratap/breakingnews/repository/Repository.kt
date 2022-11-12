package com.noreplypratap.breakingnews.repository

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.api.NewsService
import com.noreplypratap.breakingnews.db.NewsArticleDao
import com.noreplypratap.breakingnews.model.Article
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val newsService: NewsService , private val newsArticleDao: NewsArticleDao) {

    suspend fun getNewsData(countryCode : String,pageNumber: Int): Response<NewsData> {
        return newsService.loadNewsFormAPI(countryCode,pageNumber)
    }

    suspend fun searchNewsData(queryString : String,pageNumber: Int): Response<NewsData> {
        return newsService.searchNews(queryString,pageNumber)
    }

    suspend fun saveArticleToDB(article: List<Article>) {
        newsArticleDao.saveArticleToDatabase(article)
    }

    fun getArticleFromDB() = newsArticleDao.getArticlesFromDatabase()

}
