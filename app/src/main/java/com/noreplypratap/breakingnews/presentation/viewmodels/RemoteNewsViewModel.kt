package com.noreplypratap.breakingnews.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.model.RemoteUseCases
import com.noreplypratap.domain.usecases.remote.RemoteArticlesUseCase
import com.noreplypratap.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteNewsViewModel @Inject constructor(
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _remoteArticles = MutableLiveData<Resource<List<NewsArticle>>>()
    val remoteArticles: LiveData<Resource<List<NewsArticle>>> get() = _remoteArticles
    fun getBreakingNews(countryCode: String, category: String = "", query: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            remoteUseCases.remoteArticlesUseCase(countryCode, category, query).collect { data ->
                _remoteArticles.postValue(data)
            }
        }
    }

}
