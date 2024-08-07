package com.example.news.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.models.Article
import com.example.news.models.NewsResponse
import com.example.news.repository.NewsRepository
import com.example.news.api.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository): ViewModel() {

    val news: MutableLiveData<HttpResponse<NewsResponse>> = MutableLiveData()
    init {
        getNews("bbc-news,the-hindu,the-times-of-india,ars-technica,wired,usa-today,time,polygon,politico,new-york-magazine,nbc-news,national-geographic,msnbc,fortune,espn,cnn,bloomberg,axios")
    }
    // Handling Backend
    fun addNews(article: Article)=viewModelScope.launch(Dispatchers.IO) {
        newsRepository.addNews(article)
    }
    fun deleteNews(article: Article)=viewModelScope.launch(Dispatchers.IO) {
        newsRepository.deleteNews(article)
    }
    fun savedNews() = newsRepository.getNewsFromDB()

    // Handling API Calls
    fun getNews(country:String)=viewModelScope.launch(Dispatchers.IO) {
        news.postValue(HttpResponse.Loading())
        val response = newsRepository.getBreakingNews(country)
        news.postValue(handleNewsResponse(response))
    }
    fun searchedNews(query:String)=viewModelScope.launch(Dispatchers.IO) {
        news.postValue(HttpResponse.Loading())
        val response = newsRepository.queryNews(query)
        news.postValue(handleNewsResponse(response))
    }
    fun handleNewsResponse(response: Response<NewsResponse>): HttpResponse<NewsResponse> {
        if(response.isSuccessful){
            response.body()?.let {
                return HttpResponse.Success(it)
            }
        }
        return HttpResponse.Error(message = response.message())
    }
    fun isNewsPresent(newsURL: String)= newsRepository.isNewsPresent(newsURL)
}
