package com.noreplypratap.breakingnews.presentation.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.core.utils.loadImageFromUrl

@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.context.loadImageFromUrl(url,this)
    } else {
        this.setImageResource(R.drawable.launcher_new)
    }
}