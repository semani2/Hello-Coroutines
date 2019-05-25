package com.sai.news_app.respository

import com.sai.news_app.BuildConfig
import com.sai.news_app.await
import com.sai.news_app.db.NewsArticleDao
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.network.NewsApiService

class NewsRepository(val api: NewsApiService, val db: NewsArticleDao): INewsRepository {

    companion object {
        private val TAG = NewsRepository::class.java.simpleName
    }

    override suspend fun getNews() : List<NewsArticle> {
        try {
            val news = api.getNews(BuildConfig.NEWS_API_KEY).await()

            db.deleteAllArticles()

            for(article in news.articles) {
                val newsArticle = NewsArticle(
                    id = 0,
                    title = article.title,
                    description = article.description,
                    imageUrl = article.urlToImage,
                    url = article.url,
                    source = article.source.name,
                    publishedAt = article.publishedAt
                )
                db.insertNewsArticle(newsArticle)
            }

            return db.getNewsArticles()
        }
        catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getNewsArticle(id: Int): NewsArticle? {
        return try {
            db.getNewsArticles().first {
                it.id == id
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
