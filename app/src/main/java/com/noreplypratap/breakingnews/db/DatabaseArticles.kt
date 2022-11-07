package com.noreplypratap.breakingnews.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noreplypratap.breakingnews.model.Article

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class DatabaseArticles : RoomDatabase() {
    abstract fun getArticleDao() : NewsDao

    companion object{
        @Volatile
        private var instance : DatabaseArticles? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            DatabaseArticles::class.java,
            "article_db.db"
        ).build()
    }
}

