package com.example.news.database

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.example.news.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(NewsConverters::class)
abstract class NewsDatabase:RoomDatabase() {
    abstract fun newsDao():NewsDao
    companion object{
        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }




}