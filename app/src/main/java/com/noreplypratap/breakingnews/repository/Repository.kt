package com.noreplypratap.breakingnews.repository

import androidx.lifecycle.LiveData
import com.noreplypratap.breakingnews.data.remote.NewsService
import com.noreplypratap.breakingnews.data.local.NewsArticleDao
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData
import retrofit2.Response
import javax.inject.Inject
class Repository @Inject constructor(
    private val newsService: NewsService,
    private val newsArticleDao: NewsArticleDao
) {
    //api
    suspend fun loadArticles(
        countryCode: String,
        category: String = "",
        q: String = ""
    ): Response<NewsData> {
        return newsService.getLiveArticles(countryCode, category, q)
    }

    //api
    suspend fun searchArticlesOnline(queryString: String): Response<NewsData> {
        return newsService.searchArticles(queryString)
    }

    //LOCAL DB
    suspend fun updateArticle(article: Article) {
        newsArticleDao.updateArticle(article)
    }
    suspend fun saveArticle(article: Article) {
        newsArticleDao.saveArticle(article)
    }
    suspend fun saveArticles(articles: MutableList<Article>) {
        newsArticleDao.createArticles(articles)
    }

    suspend fun deleteEverything() {
        newsArticleDao.deleteEverything()
    }
    fun readArticles(): LiveData<List<Article>> {
        return newsArticleDao.readArticles()
    }

}



