package com.noreplypratap.breakingnews.newsapi

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET


interface NewsService {

    @GET("/v2/top-headlines?country=in&apiKey=${Constants.API_Key}")
    suspend fun loadNewsFormAPI(): Response<NewsData>

}