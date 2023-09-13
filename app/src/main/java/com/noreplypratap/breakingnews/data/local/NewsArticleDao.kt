package com.noreplypratap.breakingnews.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noreplypratap.breakingnews.model.Article

@Dao
interface NewsArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createArticles(article: List<Article>)
    @Query("SELECT * From articles")
    fun readArticles() : LiveData<List<Article>>
    @Update
    suspend fun updateArticle(article: Article)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArticle(article: Article)
    @Update
    suspend fun updateArticle(articles: List<Article>)

    @Query("DELETE FROM articles")
    suspend fun deleteEverything()

}