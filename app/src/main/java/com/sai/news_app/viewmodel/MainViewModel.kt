package com.sai.news_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.respository.INewsRepository

class MainViewModel(private val repository: INewsRepository) : NewsViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    private val _news = MutableLiveData<List<NewsArticle>>()

    val news: LiveData<List<NewsArticle>>
        get() = _news

    fun fetchNews() {
        loadData ({
            repository.getNews()
        }) {
            _news.value = it
        }
    }
}
