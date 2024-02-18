package com.noreplypratap.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.noreplypratap.data.model.local.NewsArticleEntity

@Database(
    entities = [NewsArticleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DatabaseArticles : RoomDatabase() {
    abstract fun getArticleDao() : NewsArticleDao

    companion object{
        @Volatile
        private var instance : DatabaseArticles? = null

        fun createDatabase(context: Context) : DatabaseArticles {

            if (instance == null){
                synchronized(this){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseArticles::class.java,
                        "OfflineNewsDataBase"
                    ).build()
                }
            }
            return instance!!
        }
    }
}