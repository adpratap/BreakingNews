package com.noreplypratap.breakingnews.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noreplypratap.breakingnews.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upDate(article: Article):Long

    @Query("SELECT * From articles")
    fun getArticles() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticles(article: Article)

}