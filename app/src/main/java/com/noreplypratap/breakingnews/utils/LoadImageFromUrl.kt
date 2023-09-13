package com.noreplypratap.breakingnews.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.noreplypratap.breakingnews.R
import java.lang.Exception

@SuppressLint("UseCompatLoadingForDrawables")
fun Context.loadImageFromUrl(newsUrl : String?, imageView: ImageView){
    try {
        if (!newsUrl.isNullOrBlank() && newsUrl.isNotEmpty()){
            Glide.with(this).load(newsUrl).error(getDrawable(R.drawable.launcher_new))
                .into(imageView)
        }else {
            imageView.setImageResource(R.drawable.launcher_new)
        }
    } catch (e: Exception) {
        logMessage("loadImageFromUrl","Exception")
    }

}
