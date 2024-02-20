package com.noreplypratap.breakingnews.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.model.RemoteUseCases
import com.noreplypratap.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val remoteUseCases: RemoteUseCases
) : ViewModel() {
    private val _searchNewsArticles = MutableLiveData<Resource<List<NewsArticle>>>()
    val searchNewsArticles: LiveData<Resource<List<NewsArticle>>> get() = _searchNewsArticles
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        remoteUseCases.searchArticlesUseCase(searchQuery).collect {
            _searchNewsArticles.postValue(it)
        }
    }
}