package com.noreplypratap.domain.usecases.local

import com.noreplypratap.domain.repository.LocalRepository
import javax.inject.Inject

class ReadArticleUseCase@Inject constructor(
    private val localRepository: LocalRepository
) {
    operator fun invoke() = localRepository.readArticles()
}