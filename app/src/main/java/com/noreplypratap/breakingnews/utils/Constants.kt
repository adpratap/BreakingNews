package com.noreplypratap.breakingnews.utils

import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData

object Constants {

    const val APP_Title = "Breaking News"
    const val Base_URL = "https://newsapi.org/"
    const val API_Key = "ff82a304e0844b7d97466153e04030dd"
    const val LOG_TAG = "MVVMOBreakingNews"

    var nextNewsForFragment: Article? = null
    var nextNewsDatas: NewsData? = null
    var netStatus: Boolean = true

}
