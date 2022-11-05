package com.noreplypratap.breakingnews.utils

import androidx.lifecycle.LiveData
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData

object Constants {
    const val APP_Title = "Breaking News"
    const val Base_URL = "https://newsapi.org/"
    const val DefaultImageURl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQsq1NacYKHKS-RudSBgbLZa_ndkD-lmmQfA&usqp=CAU"
    const val API_Key = ""
    const val LOG_TAG = "MVVMOBreakingNews"
    lateinit var onClickData : LiveData<Boolean>
    var nextNewsForFragment: Article? = null
    var nextNewsDatas: NewsData? = null
    var netStatus: Boolean = true
}
