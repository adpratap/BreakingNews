package com.noreplypratap.breakingnews.data.remote

import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/top-headlines")
    suspend fun getLiveArticles(
        @Query("country")
        countryCode : String,
        @Query("category")
        category : String,
        @Query("q")
        q : String,
        @Query("page")
        pageNo : Int = 1,
        @Query("pageSize")
        pageSize : Int = Constants.pageSize,
        @Query("apikey")
        apiKey : String = Constants.APIKey
    ): Response<NewsData>

    @GET("/v2/everything")
    suspend fun searchArticles(
        @Query("q")
        query : String,
        @Query("sortBy")
        sortBy : String = Constants.sortBy,
        @Query("language")
        language : String = Constants.language,
        @Query("from")
        dateFrom : String = "",
        @Query("to")
        dateTo : String = "",
        @Query("page")
        pageNo : Int = 1,
        @Query("pageSize")
        pageSize : Int = Constants.pageSize,
        @Query("apikey")
        apiKey : String = Constants.APIKey
    ): Response<NewsData>

}