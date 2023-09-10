package com.noreplypratap.breakingnews.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import java.net.InetSocketAddress
import java.net.Socket

fun Context.showToast(toastString : String) {
    Toast.makeText(this,toastString, Toast.LENGTH_SHORT).show()
}

fun logging(msg : String){
    Log.d(Constants.TAG,msg)
}

fun Context.webBuilder(urlString : String) {
    CustomTabsIntent.Builder().build().launchUrl(this,Uri.parse(urlString))
}

fun Context.glide(url : String,imageView: ImageView){
    Glide.with(this).load(url)
        .into(imageView)
}


fun netStatusCheck() : Boolean {
    return try {
        val socket = Socket()
        socket.connect(InetSocketAddress("8.8.8.8",53))
        socket.close()
        true
    } catch ( e: Exception){
        e.printStackTrace()
        false
    }
}

