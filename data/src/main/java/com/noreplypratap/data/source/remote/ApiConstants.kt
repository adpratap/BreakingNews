package com.noreplypratap.data.source.remote

import com.noreplypratap.data.utils.getAPIKey

object ApiConstants {
    const val BaseURL = "https://newsapi.org/"
    val APIKey = getAPIKey()
    const val language = "en"
    const val sortBy = "popularity"
}
