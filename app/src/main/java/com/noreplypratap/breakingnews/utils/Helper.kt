package com.noreplypratap.breakingnews.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.browser.customtabs.CustomTabsIntent
import com.noreplypratap.breakingnews.model.Article
import com.noreplypratap.breakingnews.model.NewsData
import com.noreplypratap.breakingnews.service.NewsService
import retrofit2.Response


fun Context.webBuilder(urlString : String) {
    CustomTabsIntent.Builder().build().launchUrl(this,Uri.parse(urlString))
}

fun Context.sendIntent(action: String) {
    startNewsService { intent ->
        intent.apply {
            putExtra("action", action)
        }
    }
}

fun Context.startNewsService(
    callback: ((intent: Intent) -> Any)? = null
) {
    val intent = Intent(this, NewsService::class.java)
    callback?.let {
        callback(intent)
    }
    startForegroundService(intent)
}


fun handleResponse(response: Response<NewsData>): Resource<List<Article>> {
    if (response.isSuccessful) {
        response.body()?.let {
            return Resource.Success(it.articles)
        }
    }
    return Resource.Error(response.message())
}


