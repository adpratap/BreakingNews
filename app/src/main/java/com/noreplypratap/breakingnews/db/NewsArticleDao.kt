package com.noreplypratap.breakingnews.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noreplypratap.breakingnews.model.Article

@Dao
interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticleToDatabase(article: List<Article>)

    @Query("SELECT * From articles")
    fun getArticlesFromDatabase() : LiveData<List<Article>>

    @Query("DELETE FROM articles")
    suspend fun deleteDB()

}