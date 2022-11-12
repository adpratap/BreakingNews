package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.LiveData
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
class BreakingNewsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val breakingNews : MutableLiveData<Resource<NewsData>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse : NewsData? = null

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = repository.getNewsData(countryCode, breakingNewsPage)
        breakingNews.postValue(handleNewsResponse(response))
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

    fun getSavedBreakingNews() : LiveData<List<Article>> {
        return repository.getArticleFromDB()
    }

    fun saveNews(articles: List<Article>) = viewModelScope.launch {
        repository.saveArticleToDB(articles)
    }
}
