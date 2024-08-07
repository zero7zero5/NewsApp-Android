package com.example.news.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.models.Article


class NewsAdapter(val application: Application):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(view: View):RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.ivArticleImage)
        val title = view.findViewById<TextView>(R.id.tvTitle)
        val description = view.findViewById<TextView>(R.id.tvDescription)
        val publishedAt = view.findViewById<TextView>(R.id.tvPublishedAt)
        val source = view.findViewById<TextView>(R.id.tvSource)
        val articleContainer = view.findViewById<ConstraintLayout>(R.id.articleContainer)

    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false)
        return NewsViewHolder(view)
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.apply {
            Glide.with(application).load(article.urlToImage).into(image)
            source.text = article.source.name
            title.text = article.title
            description.text = article.description
            publishedAt.text = article.publishedAt

            articleContainer.setOnClickListener{
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener :((Article)->Unit)?=null

    fun setOnItemClickListener(listener:((Article)->Unit)){
        onItemClickListener=listener
    }
    override fun getItemCount(): Int {
       return  differ.currentList.size
    }

}