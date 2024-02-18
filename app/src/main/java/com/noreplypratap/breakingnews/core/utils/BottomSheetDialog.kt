package com.noreplypratap.breakingnews.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.noreplypratap.breakingnews.R
import com.noreplypratap.domain.model.NewsArticle

@SuppressLint("SetTextI18n")
fun Context.showBottomSheetDialog(article: NewsArticle, btnStatus: Boolean = false, deleteArticle : () -> Unit = {}, saveArticle : () -> Unit = {}) {
    val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
    val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null)

    this.loadImageFromUrl(
        article.urlToImage,
        view.findViewById(R.id.articleImageView)
    )

    if (btnStatus) {
        view.findViewById<Button>(R.id.btnDeleteArticle).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                deleteArticle()
                bottomSheetDialog.dismiss()
            }
        }
    } else {
        view.findViewById<Button>(R.id.btnSaveArticle).apply {
            visibility = View.VISIBLE
            setOnClickListener {
                saveArticle()
            }
        }
    }

    if (checkForNull(article.title)){
        view.findViewById<TextView>(R.id.articleTitleTextView).apply {
            visibility = View.VISIBLE
            text = article.title.toString()
        }
    }
    if (checkForNull(article.description)){
        view.findViewById<TextView>(R.id.articleDescriptionTextView).apply {
            visibility = View.VISIBLE
            text = article.description.toString()
        }
    }
    if (checkForNull(article.content)){
        view.findViewById<TextView>(R.id.articleContentTextView).apply {
            visibility = View.VISIBLE
            text = article.content.toString()
        }
    }
    if (checkForNull(article.author)){
        view.findViewById<TextView>(R.id.articleAuthorTextView).apply {
            visibility = View.VISIBLE
            text = "Author: ${article.author.toString()}"
        }
    }
    if (checkForNull(article.publishedAt)){
        view.findViewById<TextView>(R.id.articleDateTextView).apply {
            visibility = View.VISIBLE
            text = "Published on: ${article.publishedAt.toString()}"
        }
    }
    view.findViewById<Button>(R.id.btnURL).setOnClickListener { _ ->
        if (this.isOnline()) {
            article.url?.let { it1 -> this.webBuilder(it1) }
        }
    }

    if (checkForNull(null)){
        view.findViewById<TextView>(R.id.articleSourceTextView).apply {
            visibility = View.VISIBLE
            text = "Source: "
        }
    }
    bottomSheetDialog.setContentView(view)
    bottomSheetDialog.show()
}

fun checkForNull(data: String?): Boolean {
    return !data.isNullOrEmpty()
}