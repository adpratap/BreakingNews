package com.noreplypratap.domain.usecases.remote

import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.repository.RemoteRepository
import com.noreplypratap.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    operator fun invoke(
        countryCode: String,
        category: String = "",
        query: String = ""
    ): Flow<Resource<List<NewsArticle>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = repository.getArticles(countryCode, category, query)
                if (response.isNotEmpty()) {
                    emit(Resource.Success(response))
                } else {
                    emit(Resource.Error("Response Error"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception $e"))
            } catch (e: HttpException) {
                emit(Resource.Error("HttpException $e"))
            }
        }
}