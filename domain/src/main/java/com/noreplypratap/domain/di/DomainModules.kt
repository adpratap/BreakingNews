package com.noreplypratap.domain.di

import com.noreplypratap.domain.repository.LocalRepository
import com.noreplypratap.domain.repository.RemoteRepository
import com.noreplypratap.domain.usecases.local.CreateArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteDatabaseUseCase
import com.noreplypratap.domain.usecases.local.ReadArticleUseCase
import com.noreplypratap.domain.usecases.local.UpdateArticleUseCase
import com.noreplypratap.domain.usecases.remote.GetArticlesUseCase
import com.noreplypratap.domain.usecases.remote.SearchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModules {

    // Remote
    @Provides
    @Singleton
    fun provideGetNewsArticlesUseCase(repository: RemoteRepository): GetArticlesUseCase =
        GetArticlesUseCase(repository)

    @Provides
    @Singleton
    fun provideSearchNewsArticlesUseCase(repository: RemoteRepository): SearchArticlesUseCase =
        SearchArticlesUseCase(repository)

    // Local

    @Provides
    @Singleton
    fun provideCreateArticleUseCase(localRepository: LocalRepository): CreateArticleUseCase =
        CreateArticleUseCase(localRepository)

    @Provides
    @Singleton
    fun provideDeleteArticleUseCase(localRepository: LocalRepository): DeleteArticleUseCase =
        DeleteArticleUseCase(localRepository)

    @Provides
    @Singleton
    fun provideDeleteDatabaseUseCase(localRepository: LocalRepository): DeleteDatabaseUseCase =
        DeleteDatabaseUseCase(localRepository)

    @Provides
    @Singleton
    fun provideReadArticleUseCase(localRepository: LocalRepository): ReadArticleUseCase =
        ReadArticleUseCase(localRepository)

    @Provides
    @Singleton
    fun provideUpdateArticleUseCase(localRepository: LocalRepository): UpdateArticleUseCase =
        UpdateArticleUseCase(localRepository)

}