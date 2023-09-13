package com.noreplypratap.breakingnews.di

import android.app.Application
import com.noreplypratap.breakingnews.data.remote.NewsService
import com.noreplypratap.breakingnews.data.local.DatabaseArticles
import com.noreplypratap.breakingnews.data.local.NewsArticleDao
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Constants
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
object Modules {

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@BaseURl base_URL: String , okHttpClient: OkHttpClient ): Retrofit {
        return Retrofit.Builder().baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideRepository(newsService: NewsService,newsArticleDao: NewsArticleDao): Repository {
        return Repository(newsService,newsArticleDao)
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
        return Constants.APIKey
    }

    @Provides
    @BaseURl
    fun provideBaseURL(): String {
        return Constants.BaseURL
    }
}
