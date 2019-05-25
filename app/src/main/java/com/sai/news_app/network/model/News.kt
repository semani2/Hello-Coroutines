package com.sai.news_app.network.model

data class News(var status: String = "", var totalResults: Int, var articles: List<Article>)
