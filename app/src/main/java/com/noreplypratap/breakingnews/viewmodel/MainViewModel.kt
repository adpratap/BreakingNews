package com.noreplypratap.breakingnews.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.Constants.LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _responseLiveNewsData = MutableLiveData<NewsData>()

    val responseLiveNewsData: LiveData<NewsData>
        get() = _responseLiveNewsData

    fun getBreakingNewsData() = viewModelScope.launch {
        repository.getNewsData().let {
            val status = it.body()?.status
            if (status == "ok") {
                _responseLiveNewsData.postValue(it.body())
            } else {
                //_responseLiveNewsData.postValue(it.body())
                Log.d(LOG_TAG, "No Data API Error")
            }
        }
    }


}