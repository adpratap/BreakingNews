package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Resource
import com.noreplypratap.breakingnews.utils.handleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _remoteArticles = MutableLiveData<Resource<List<Article>>>()
    val remoteArticles: LiveData<Resource<List<Article>>> get() = _remoteArticles
    fun getBreakingNews(countryCode: String, category: String = "", query: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            _remoteArticles.postValue(Resource.Loading())
            val response = repository.loadArticles(countryCode, category, query)
            _remoteArticles.postValue(handleResponse(response))
        }
    }

}
