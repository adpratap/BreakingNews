package com.noreplypratap.breakingnews.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

fun Context.isOnline() : Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            logMessage("InternetStatus", "Online", Log.INFO)
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            logMessage("InternetStatus", "Online", Log.INFO)
            return true
        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            logMessage("InternetStatus", "Online", Log.INFO)
            return true
        }
    }
    logMessage("InternetStatus", "No Internet", Log.INFO)
    this.showToastMessage("No Internet")
    return false
}
