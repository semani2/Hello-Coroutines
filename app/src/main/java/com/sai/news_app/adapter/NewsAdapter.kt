package com.sai.news_app.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sai.news_app.R
import com.sai.news_app.activity.DetailActivity
import com.sai.news_app.db.entity.NewsArticle
import com.sai.news_app.listen
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val items: List<NewsArticle>, private val activity: Activity)
    : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.news_item, parent, false))
            .listen { position: Int, _: Int ->
                val newsArticle = items[position]

                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.PARAM_NEWS_ID, newsArticle.id)
                activity.startActivity(intent)
            }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items[position]
        article.imageUrl?.let {
            Glide.with(activity)
                .load(Uri.parse(article.imageUrl))
                .into(holder.newsImageView)
        }
        holder.titleTextView.text = article.title
        holder.contentTextView.text = article.description
        holder.sourceTextView.text = article.source
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsImageView: ImageView = view.news_image_view
        val titleTextView: TextView = view.title_text_view
        val contentTextView: TextView = view.content_text_view
        val sourceTextView: TextView = view.source_text_view
    }
}
