package com.noreplypratap.data.di

import android.app.Application
import com.noreplypratap.data.repository.LocalRepositoryImpl
import com.noreplypratap.data.repository.RemoteRepositoryImpl
import com.noreplypratap.data.source.local.DatabaseArticles
import com.noreplypratap.data.source.local.NewsArticleDao
import com.noreplypratap.data.source.remote.NewsApiService
import com.noreplypratap.data.source.remote.ApiConstants
import com.noreplypratap.domain.repository.LocalRepository
import com.noreplypratap.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {

    @Provides
    @Singleton
    fun provideRemoteRepository(newsApiService: NewsApiService) : RemoteRepository {
        return RemoteRepositoryImpl(newsApiService)
    }

    @Provides
    @Singleton
    fun provideLocalRepository(newsArticleDao: NewsArticleDao): LocalRepository {
        return LocalRepositoryImpl(newsArticleDao)
    }

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@BaseURl baseURL: String, okHttpClient: OkHttpClient ): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application) : DatabaseArticles {
        return DatabaseArticles.createDatabase(context)
    }

    @Provides
    @Singleton
    fun provideNewsDao(databaseArticles: DatabaseArticles) : NewsArticleDao {
        return databaseArticles.getArticleDao()
    }

    @Provides
    @APIKey
    fun provideAPIKey(): String {
        return ApiConstants.APIKey
    }

    @Provides
    @BaseURl
    fun provideBaseURL(): String {
        return ApiConstants.BaseURL
    }

}
