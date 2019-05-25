package com.sai.news_app.respository

import com.sai.news_app.db.entity.NewsArticle

interface INewsRepository {

    suspend fun getNews(): List<NewsArticle>

    suspend fun getNewsArticle(id: Int): NewsArticle?
}
