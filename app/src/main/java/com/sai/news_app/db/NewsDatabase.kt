package com.sai.news_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sai.news_app.db.entity.NewsArticle

@Database(entities = [NewsArticle::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsArticleDao(): NewsArticleDao

    companion object {
        lateinit var INSTANCE: NewsDatabase

        fun getDatabase(context: Context): NewsDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(NewsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsDatabase::class.java,
                        "newsDB").build()
                }
            }
            return INSTANCE
        }
    }
}
