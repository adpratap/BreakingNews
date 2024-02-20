package com.noreplypratap.domain.model

import com.noreplypratap.domain.usecases.local.CreateArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteDatabaseUseCase
import com.noreplypratap.domain.usecases.local.ReadArticleUseCase
import com.noreplypratap.domain.usecases.local.UpdateArticleUseCase

data class LocalUseCases(
    val createArticleUseCase: CreateArticleUseCase,
    val readArticleUseCase: ReadArticleUseCase,
    val updateArticleUseCase: UpdateArticleUseCase,
    val deleteDatabaseUseCase: DeleteDatabaseUseCase,
    val deleteArticleUseCase: DeleteArticleUseCase
)