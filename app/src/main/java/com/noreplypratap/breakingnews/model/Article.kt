package com.noreplypratap.breakingnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    var isStared: Boolean = false
)