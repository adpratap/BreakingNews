package com.noreplypratap.breakingnews.di

import com.noreplypratap.breakingnews.newsapi.NewsService
import com.noreplypratap.breakingnews.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ModuleHilt {

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(@BaseURl base_URL: String): Retrofit {
        return Retrofit.Builder().baseUrl(base_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @BaseURl
    fun provideAPIKey(): String {
        return Constants.Base_URL
    }

    @Provides
    @APIKey
    fun provideBaseURL(): String {
        return Constants.Base_URL
    }


}