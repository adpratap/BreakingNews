package com.noreplypratap.breakingnews.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.noreplypratap.breakingnews.core.services.NewsService


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



