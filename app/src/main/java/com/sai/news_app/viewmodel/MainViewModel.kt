package com.sai.news_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.respository.INewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: INewsRepository) : ViewModel() {

    companion object {
        val TAG = MainViewModel::class.java.simpleName
    }

    private val _news = MutableLiveData<List<NewsArticle>>()

    val news: LiveData<List<NewsArticle>>
        get() = _news

    private val _spinner = MutableLiveData<Boolean>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    fun fetchNews() {
        loadData ({
            repository.getNews()
        }) {
            _news.value = it
        }
    }

    private fun <T: Any> loadData(block: suspend (() -> T?), callback: ((T?) -> Unit)): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            try {
                _spinner.value = true
                val data = withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                    block()
                }
                callback(data)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching news", e)
            } finally {
                _spinner.value = false
            }
        }
    }
}
