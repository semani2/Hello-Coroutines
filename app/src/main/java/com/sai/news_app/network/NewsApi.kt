package com.sai.news_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApi {

    companion object {
        fun create(): NewsApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/v2/")
                .build()

            return retrofit.create(NewsApiService::class.java)
        }
    }
}
