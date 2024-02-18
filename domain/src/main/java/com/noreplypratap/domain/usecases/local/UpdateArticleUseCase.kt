package com.noreplypratap.domain.usecases.local

import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.repository.LocalRepository
import javax.inject.Inject

class UpdateArticleUseCase@Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(newsArticle: NewsArticle) {
        localRepository.updateArticle(newsArticle)
    }
}