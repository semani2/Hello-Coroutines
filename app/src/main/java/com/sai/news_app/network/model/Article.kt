package com.sai.news_app.network.model

data class Article(var title: String = "",
                   var description: String = "",
                   var url: String = "",
                   var urlToImage: String = "",
                   var publishedAt: String = "",
                   var source: Source)
