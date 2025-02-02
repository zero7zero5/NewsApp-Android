package com.example.news.database

import androidx.room.TypeConverter
import com.example.news.models.Source

class NewsConverters {
    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String):Source{
        return Source(name,name)
    }

}