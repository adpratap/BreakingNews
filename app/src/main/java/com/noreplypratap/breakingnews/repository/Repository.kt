package com.noreplypratap.breakingnews.repository

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.newsapi.NewsService
import retrofit2.Response
import javax.inject.Inject


class Repository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNewsData(): Response<NewsData> {
        return newsService.loadNewsFormAPI()
    }

}
