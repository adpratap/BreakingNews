package com.noreplypratap.data.di

import android.app.Application
import androidx.room.Room
import com.noreplypratap.data.repository.LocalRepositoryImpl
import com.noreplypratap.data.repository.RemoteRepositoryImpl
import com.noreplypratap.data.source.local.NewsArticleDatabase
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
object DataModule {

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
    fun provideRetrofit(@BASE_URl baseURL: String, okHttpClient: OkHttpClient ): Retrofit {
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
    fun provideDatabase(context: Application) : NewsArticleDatabase {
        return Room.databaseBuilder(
            context,
            NewsArticleDatabase::class.java,
            NewsArticleDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(databaseArticles: NewsArticleDatabase) : NewsArticleDao {
        return databaseArticles.newsArticleDao
    }

    @Provides
    @BASE_URl
    fun provideBaseURL(): String {
        return ApiConstants.BaseURL
    }

}
