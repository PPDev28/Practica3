package com.example.practica3.retrofit

import com.example.practica3.models.WebBrowserDto
import retrofit2.Call
import retrofit2.http.GET


interface WebBrowserService {
    @GET("/v3/59bc6ad0-efef-4a44-8756-de4ca5bee87c/")
    fun getWebBrowsers(): Call<List<WebBrowserDto>>
}
