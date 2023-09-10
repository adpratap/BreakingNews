package com.noreplypratap.breakingnews.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.utils.Constants
import com.noreplypratap.breakingnews.utils.glide

@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String?) {
    if (!url.isNullOrEmpty()) {
        this.context.glide(url,this)
    } else {
        this.setImageResource(R.drawable.launcher_new)
    }
}