package com.example.news.api
import com.example.news.models.NewsResponse
import com.example.news.api.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("sources") source:String="bbc-news,the-hindu,the-times-of-india,ars-technica,wired,usa-today,time,polygon,politico,new-york-magazine,nbc-news,national-geographic,msnbc,fortune,espn,cnn,bloomberg,axios",
        @Query("page") page:Int=1,
        @Query("apiKey") apiKey:String=API_KEY
    ):Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query:String,
        @Query("page") page:Int=1,
        @Query("pageSize") pageSize:Int=20,
        @Query("apiKey") apiKey:String=API_KEY,
    ):Response<NewsResponse>
}

