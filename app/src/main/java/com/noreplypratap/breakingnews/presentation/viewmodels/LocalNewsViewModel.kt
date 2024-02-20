package com.noreplypratap.breakingnews.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.domain.model.LocalUseCases
import com.noreplypratap.domain.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalNewsViewModel @Inject constructor(
    private val localUseCases: LocalUseCases
) : ViewModel() {
    private val _savedArticles =  MutableLiveData<List<NewsArticle>>()
    val savedArticles: LiveData<List<NewsArticle>> get() = _savedArticles

    init {
        readArticle()
    }
    fun createArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        localUseCases.createArticleUseCase(newsArticle)
    }

    private fun readArticle() = viewModelScope.launch(Dispatchers.IO) {
        localUseCases.readArticleUseCase().collect {
            _savedArticles.postValue(it)
        }
    }

    fun updateArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        localUseCases.updateArticleUseCase(newsArticle)
    }

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        localUseCases.deleteArticleUseCase(newsArticle)
    }

    fun deleteEverything() = viewModelScope.launch(Dispatchers.IO) {
        localUseCases.deleteDatabaseUseCase()
    }

}