package com.sai.news_app.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sai.news_app.db.entity.NewsArticle

@Dao
interface NewsArticleDao {
    @Insert
    suspend fun insertNewsArticle(article: NewsArticle)

    @Delete
    suspend fun deleteNewsArticle(article: NewsArticle)

    @Query("SELECT * FROM NewsArticle")
    suspend fun getNewsArticles(): List<NewsArticle>

    @Query("DELETE FROM NewsArticle")
    suspend fun deleteAllArticles()
}
