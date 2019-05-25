package com.sai.news_app.network

import com.sai.news_app.network.model.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines?country=us")
    fun getNews(@Query("apiKey") apiKey: String): Call<News>
}
