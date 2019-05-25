package com.sai.news_app.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sai.news_app.R
import com.sai.news_app.db.entity.NewsArticle
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val items: List<NewsArticle>, private val context: Context)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.news_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items[position]
        article.imageUrl?.let {
            Glide.with(context)
                .load(Uri.parse(article.imageUrl))
                .into(holder.newsImageView)
        }
        holder.titleTextView.text = article.title
        holder.contentTextView.text = article.description
        holder.dateTextView.text = article.publishedAt
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImageView: ImageView = view.news_image_view
        val titleTextView: TextView = view.title_text_view
        val contentTextView: TextView = view.content_text_view
        val dateTextView: TextView = view.date_text_view
    }
}
