package com.noreplypratap.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noreplypratap.data.model.local.NewsArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createArticle(article: NewsArticleEntity)
    @Query("SELECT * From articles")
    fun readArticles() : Flow<List<NewsArticleEntity>>
    @Update
    suspend fun updateArticle(article: NewsArticleEntity)

    @Delete
    suspend fun deleteArticle(article: NewsArticleEntity)

    @Query("DELETE FROM articles")
    suspend fun deleteEverything()

}