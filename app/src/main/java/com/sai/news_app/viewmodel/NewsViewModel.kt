package com.sai.news_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class NewsViewModel : ViewModel() {
    private val _spinner = MutableLiveData<Boolean>()

    val spinner: LiveData<Boolean>
        get() = _spinner

    protected fun <T: Any> loadData(block: suspend (() -> T?), callback: ((T?) -> Unit)): Job {
        return viewModelScope.launch(Dispatchers.Main) {
            try {
                _spinner.value = true
                val data = withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                    block()
                }
                callback(data)
            } catch (e: Exception) {
                Log.e(MainViewModel.TAG, "Error fetching news", e)
            } finally {
                _spinner.value = false
            }
        }
    }
}
