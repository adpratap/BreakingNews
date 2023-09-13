package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDBViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    //ForAllNewsFromRoom

    val savedArticles: LiveData<List<Article>> get() = repository.readArticles()

    fun saveArticles(articles: MutableList<Article>) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveArticles(articles)
    }

    fun deleteEverything() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteEverything()
    }

    fun updateArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateArticle(article)
    }

    fun saveArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveArticle(article)
    }

}