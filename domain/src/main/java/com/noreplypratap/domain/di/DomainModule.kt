package com.noreplypratap.domain.di

import com.noreplypratap.domain.repository.LocalRepository
import com.noreplypratap.domain.repository.RemoteRepository
import com.noreplypratap.domain.usecases.local.CreateArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteDatabaseUseCase
import com.noreplypratap.domain.model.LocalUseCases
import com.noreplypratap.domain.usecases.local.ReadArticleUseCase
import com.noreplypratap.domain.usecases.local.UpdateArticleUseCase
import com.noreplypratap.domain.usecases.remote.RemoteArticlesUseCase
import com.noreplypratap.domain.model.RemoteUseCases
import com.noreplypratap.domain.usecases.local.DeleteArticleUseCase
import com.noreplypratap.domain.usecases.remote.SearchArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    // Remote UseCases
    @Provides
    @Singleton
    fun provideRemoteUseCase(repository: RemoteRepository): RemoteUseCases {
        return RemoteUseCases(
            remoteArticlesUseCase = RemoteArticlesUseCase(repository),
            searchArticlesUseCase = SearchArticlesUseCase(repository)
        )
    }

    // Local UseCases
    @Provides
    @Singleton
    fun provideLocalUseCase(localRepository: LocalRepository): LocalUseCases {
        return LocalUseCases(
            createArticleUseCase = CreateArticleUseCase(localRepository),
            readArticleUseCase = ReadArticleUseCase(localRepository),
            updateArticleUseCase = UpdateArticleUseCase(localRepository),
            deleteDatabaseUseCase = DeleteDatabaseUseCase(localRepository),
            deleteArticleUseCase = DeleteArticleUseCase(localRepository)
        )
    }
}