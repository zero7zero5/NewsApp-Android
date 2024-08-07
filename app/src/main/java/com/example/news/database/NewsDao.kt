package com.example.news.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.example.news.models.Article

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNews(article:Article)

    @Delete
    suspend fun deleteNews(article: Article)
    @Query("SELECT * FROM NEWS_TABLE")
    fun getNews():LiveData<List<Article>>

    @Query("SELECT EXISTS(SELECT url FROM NEWS_TABLE WHERE url = :newsURL LIMIT 1)")
    fun isNewsPresent(newsURL: String): LiveData<Boolean>
}