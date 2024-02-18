package com.noreplypratap.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val source: String?
)
