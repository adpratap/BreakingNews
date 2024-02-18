package com.noreplypratap.breakingnews.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.usecases.local.CreateArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteArticleUseCase
import com.noreplypratap.domain.usecases.local.DeleteDatabaseUseCase
import com.noreplypratap.domain.usecases.local.ReadArticleUseCase
import com.noreplypratap.domain.usecases.local.UpdateArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalNewsViewModel @Inject constructor(
    private val createArticleUseCase: CreateArticleUseCase,
    private val readArticleUseCase: ReadArticleUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val deleteDatabaseUseCase: DeleteDatabaseUseCase
) : ViewModel() {
    private val _savedArticles =  MutableLiveData<List<NewsArticle>>()
    val savedArticles: LiveData<List<NewsArticle>> get() = _savedArticles

    init {
        readArticle()
    }
    fun createArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        createArticleUseCase(newsArticle)
    }

    fun readArticle() = viewModelScope.launch(Dispatchers.IO) {
        readArticleUseCase().collect {
            _savedArticles.postValue(it)
        }
    }

    fun updateArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        updateArticleUseCase(newsArticle)
    }

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch(Dispatchers.IO) {
        deleteArticleUseCase(newsArticle)
    }

    fun deleteEverything() = viewModelScope.launch(Dispatchers.IO) {
        deleteDatabaseUseCase()
    }

}