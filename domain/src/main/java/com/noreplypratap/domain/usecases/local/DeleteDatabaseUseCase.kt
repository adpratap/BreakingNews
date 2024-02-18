package com.noreplypratap.domain.usecases.local

import com.noreplypratap.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteDatabaseUseCase@Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke() {
        localRepository.deleteEverything()
    }
}