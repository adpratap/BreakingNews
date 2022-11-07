package com.noreplypratap.breakingnews.api

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.utils.Constants.APIKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("/v2/top-headlines")
    suspend fun loadNewsFormAPI(
        @Query("country")
        countryCode : String = "in",
        @Query("page")
        pageNo : Int = 1,
        @Query("apikey")
        apiKey : String = APIKey
    ): Response<NewsData>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q")
        query : String,
        @Query("page")
        pageNo : Int = 1,
        @Query("apikey")
        apiKey : String = APIKey
    ): Response<NewsData>

}