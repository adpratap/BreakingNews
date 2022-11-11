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

        fun createDatabase(context: Context) : DatabaseArticles {

            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseArticles::class.java,
                        "mydb"
                    ).build()
                }
            }
            return instance!!
        }
    }
}

