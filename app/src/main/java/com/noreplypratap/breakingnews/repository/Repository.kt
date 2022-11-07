package com.noreplypratap.breakingnews.repository

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.api.NewsService
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNewsData(countryCode : String,pageNumber: Int): Response<NewsData> {
        return newsService.loadNewsFormAPI(countryCode,pageNumber)
    }

    suspend fun searchNewsData(queryString : String,pageNumber: Int): Response<NewsData> {
        return newsService.searchNews(queryString,pageNumber)
    }

//    suspend fun update(article: Article) = db.getArticleDao().upDate(article)
//
//    fun saveNews() = db.getArticleDao().getArticles()
//
//    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticles(article)

}
