package com.example.practica3.retrofit

import com.example.practica3.models.WebBrowserDto
import retrofit2.Call
import retrofit2.http.GET


interface WebBrowserService {
    @GET("/v1/88d94dc0-a5cc-41ec-8618-80eae67a9472")
    fun getWebBrowsers(): Call<List<WebBrowserDto>>
}
