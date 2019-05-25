package com.sai.news_app.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sai.news_app.R
import com.sai.news_app.db.NewsDatabase
import com.sai.news_app.network.NewsApi
import com.sai.news_app.respository.NewsRepository
import com.sai.news_app.viewmodel.DetailViewModel
import com.sai.news_app.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mViewModel: DetailViewModel

    private var mNewsArticleId: Int = -1

    companion object {
        private val TAG = DetailActivity::class.java.simpleName

        @JvmStatic val PARAM_NEWS_ID = "param_news_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val database = NewsDatabase.getDatabase(this)
        val repository = NewsRepository(NewsApi.create(), database.newsArticleDao())

        mViewModel = ViewModelProviders.of(this, ViewModelFactory(repository))
            .get(DetailViewModel::class.java)

        intent?.let {
            mNewsArticleId = it.getIntExtra(PARAM_NEWS_ID, -1)
        }

        initLiveDataObservers()
    }

    override fun onResume() {
        super.onResume()
        if (mNewsArticleId == -1) {
            Log.e(TAG, "Did not receive valid news article id")
            finish()
            return
        }

        mViewModel.getNewsArticle(mNewsArticleId)
    }

    private fun initLiveDataObservers() {
        mViewModel.spinner.observe(this, Observer { isBusy ->
            isBusy?.let {
                detail_progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        mViewModel.newsArticle.observe(this, Observer { newsArticle ->
            newsArticle?.let {
                detail_title_text_view.text = it.title
                detail_desc_text_view.text = it.description
                detail_source_text_view.text = it.source
                detail_date_text_view.text = it.publishedAt

                it.imageUrl?.let { url ->
                    Glide.with(this)
                        .load(Uri.parse(url))
                        .into(detail_image_view)
                }
            }
        })
    }
}
