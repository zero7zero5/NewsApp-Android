package com.example.news.repository

import androidx.lifecycle.LiveData
import com.example.news.api.RetrofitInstance
import com.example.news.database.NewsDao
import com.example.news.models.Article

class NewsRepository(val newsDao: NewsDao) {

    // from room retrofit
    suspend fun getBreakingNews(source:String) = RetrofitInstance.api.getNews(source)

    suspend fun queryNews(query:String)= RetrofitInstance.api.searchNews(query)


    // from room database
    fun getNewsFromDB():LiveData<List<Article>> = newsDao.getNews()

    suspend fun addNews(article:Article){
        newsDao.addNews(article)
    }
    suspend fun deleteNews(article:Article){
        newsDao.deleteNews(article)
    }
    fun isNewsPresent(newsURL:String):LiveData<Boolean>{
        return newsDao.isNewsPresent(newsURL)
    }


}