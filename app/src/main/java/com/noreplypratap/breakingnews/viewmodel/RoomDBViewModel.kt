package com.noreplypratap.breakingnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDBViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    //ForAllNewsFromRoom
    fun getSavedNews() : LiveData<List<Article>> = repository.getArticleRoomDB()

    //SaveListOfNewsArticle
    fun saveArticles(articles: MutableList<Article>) = viewModelScope.launch {
        repository.saveArticles(articles)
    }

    //SaveFavNews
    fun saveFavNews(article: Article) = viewModelScope.launch {
        repository.saveFavNews(article)
    }

    //deleteAllNews
    fun deleteFavNews(favNews: Article) = viewModelScope.launch {
        repository.deleteFavNew(favNews)
    }
}