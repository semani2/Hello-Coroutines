package com.sai.news_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.respository.INewsRepository

class DetailViewModel(private val repository: INewsRepository) : NewsViewModel() {

    companion object {
        val TAG = DetailViewModel::class.java.simpleName
    }

    private val _news_article = MutableLiveData<NewsArticle>()

    val newsArticle: LiveData<NewsArticle>
    get() = _news_article

    fun getNewsArticle(id: Int) {
        loadData({
            repository.getNewsArticle(id)
        }) {
            _news_article.value = it
        }
    }
}
