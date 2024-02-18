package com.noreplypratap.data.source.remote

import com.noreplypratap.data.model.remote.NewsDataDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getArticles(
        @Query("country")
        countryCode : String,
        @Query("category")
        category : String,
        @Query("q")
        q : String,
        @Query("page")
        pageNo : Int = 1,
        @Query("pageSize")
        pageSize : Int = 10,
        @Query("apikey")
        apiKey : String = ApiConstants.APIKey
    ): Response<NewsDataDTO>

    @GET("/v2/everything")
    suspend fun searchArticles(
        @Query("q")
        query : String,
        @Query("sortBy")
        sortBy : String = ApiConstants.sortBy,
        @Query("language")
        language : String = ApiConstants.language,
        @Query("from")
        dateFrom : String = "",
        @Query("to")
        dateTo : String = "",
        @Query("page")
        pageNo : Int = 1,
        @Query("pageSize")
        pageSize : Int = 10,
        @Query("apikey")
        apiKey : String = ApiConstants.APIKey
    ): Response<NewsDataDTO>

}