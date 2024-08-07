package com.example.news.api

sealed class HttpResponse<T>(val data:T?=null, val message:String?=null) {
    class Success<T>(data:T): HttpResponse<T>(data)
    class Error<T>(data:T?=null,message: String?): HttpResponse<T>(data,message)
    class Loading<T>(): HttpResponse<T>()
}