package com.noreplypratap.breakingnews.utils

object Constants {
    const val BaseURL = "https://newsapi.org/"
    const val TAG = "MVVMOBreakingNews"
    const val pageSize : Int = 5
    var pageNo : Int = 1
    val APIKey = getAPIKey()
    const val language = "en"
    const val sortBy = "popularity"
    const val CHANNEL_ID = "notification_channel"
    const val NOTIFICATION_ID = 1

}
