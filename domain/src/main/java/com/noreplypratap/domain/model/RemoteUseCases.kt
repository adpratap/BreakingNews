package com.noreplypratap.domain.model

import com.noreplypratap.domain.usecases.remote.RemoteArticlesUseCase
import com.noreplypratap.domain.usecases.remote.SearchArticlesUseCase

data class RemoteUseCases(
    val remoteArticlesUseCase: RemoteArticlesUseCase,
    val searchArticlesUseCase: SearchArticlesUseCase
)
