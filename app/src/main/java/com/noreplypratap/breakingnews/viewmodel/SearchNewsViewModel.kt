package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val searchNews: MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var searchNewsResponse: NewsData? = null

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchNewsData(searchQuery)
        searchNews.postValue(handleSearchNewsResponse(response))
    }
    private fun handleSearchNewsResponse(response: Response<NewsData>): Resource<NewsData> {
        if (response.isSuccessful) {
            response.body()?.let {
                    searchNewsResponse = it
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }
}