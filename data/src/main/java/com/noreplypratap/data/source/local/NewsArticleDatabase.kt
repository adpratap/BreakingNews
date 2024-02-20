package com.noreplypratap.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noreplypratap.data.model.local.NewsArticleEntity

@Database(
    entities = [NewsArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract val newsArticleDao : NewsArticleDao
    companion object{
        const val DATABASE_NAME = "article_db"
    }
}