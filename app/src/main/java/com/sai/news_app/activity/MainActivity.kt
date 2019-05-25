package com.sai.news_app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sai.news_app.R
import com.sai.news_app.adapter.NewsAdapter
import com.sai.news_app.db.NewsDatabase
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.network.NewsApi
import com.sai.news_app.network.NewsApiService
import com.sai.news_app.respository.NewsRepository
import com.sai.news_app.viewmodel.MainViewModel
import com.sai.news_app.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val news = mutableListOf<NewsArticle>()
    private lateinit var newsAdapter: NewsAdapter

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = NewsDatabase.getDatabase(this)
        val repository = NewsRepository(NewsApi.create(), database.newsArticleDao())

        mViewModel = ViewModelProviders.of(this, ViewModelFactory(repository))
            .get(MainViewModel::class.java)

        newsAdapter = NewsAdapter(news, this)

        news_recycler_view.layoutManager = LinearLayoutManager(this)
        news_recycler_view.adapter = newsAdapter

        initLiveDataObservers()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchNews()
    }

    private fun initLiveDataObservers() {
        mViewModel.spinner.observe(this, Observer { isBusy ->
            isBusy?.let {
                news_progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        mViewModel.news.observe(this, Observer { newsArticles ->
            newsArticles?.let {
                news.clear()
                news.addAll(newsArticles)
                newsAdapter.notifyDataSetChanged()
            }
        })
    }
}
