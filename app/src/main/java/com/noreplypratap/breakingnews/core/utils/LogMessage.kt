package com.noreplypratap.breakingnews.core.utils

import android.util.Log

fun logMessage(tag: String, message: String, logLevel: Int = Log.DEBUG) {
    when (logLevel) {
        Log.VERBOSE -> Log.v(tag, message)
        Log.DEBUG -> Log.d(tag, message)
        Log.INFO -> Log.i(tag, message)
        Log.WARN -> Log.w(tag, message)
        Log.ERROR -> Log.e(tag, message)
        else -> Log.d(tag, message)
    }
}