package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.handleResponse
import com.noreplypratap.breakingnews.utils.logMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject



@HiltViewModel
class SearchNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val TAG = "SearchNewsViewModelTag"
    private val _searchNewsArticles = MutableLiveData<Resource<List<Article>>>()
    val searchNewsArticles: LiveData<Resource<List<Article>>> get() = _searchNewsArticles
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        _searchNewsArticles.postValue(Resource.Loading())
        val response: Response<NewsData> = repository.searchArticlesOnline(searchQuery)
        _searchNewsArticles.postValue(handleResponse(response))
    }
}