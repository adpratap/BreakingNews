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
class BreakingNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var breakingNewsResponse : NewsData? = null

    fun getBreakingNews(countryCode: String,category : String,query : String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getNewsData(countryCode,category,query)
        breakingNews.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewsData>): Resource<NewsData> {
        if (response.isSuccessful) {
            response.body()?.let {
                    breakingNewsResponse = it
                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

}
