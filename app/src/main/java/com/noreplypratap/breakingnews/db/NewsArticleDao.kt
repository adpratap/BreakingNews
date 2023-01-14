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
    suspend fun saveArticle(article: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavNews(article: Article)

    @Query("SELECT * From articles")
    fun getArticlesRoomDB() : LiveData<List<Article>>

    @Query("DELETE FROM articles")
    suspend fun deleteRoomDB()

    @Delete
    suspend fun deleteFavNews(article: Article)

}