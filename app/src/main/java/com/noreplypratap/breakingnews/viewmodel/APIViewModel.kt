package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class APIViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    val breakingNews: MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsData? = null

    val searchNews: MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsData? = null


    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getNewsData(countryCode, breakingNewsPage)
        breakingNews.postValue(handleNewsResponse(response))

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchNewsData(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewsData>): Resource<NewsData> {
        if (response.isSuccessful) {
            response.body()?.let {
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = it
                } else {
                    val oldArticle = breakingNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsData>): Resource<NewsData> {
        if (response.isSuccessful) {
            response.body()?.let {
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = it
                } else {
                    val oldArticle = searchNewsResponse?.articles
                    val newArticle = it.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(searchNewsResponse ?: it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInDB(article)
    }

    fun getSavedNews() = repository.getDataFromDB()

    fun deleteArticles(article: Article) = viewModelScope.launch {
        repository.deleteAllDataInDB(article)
    }

}