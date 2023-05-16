package com.example.practica3.retrofit

import com.example.practica3.models.WebBrowserDto
import retrofit2.Call
import retrofit2.http.GET


interface WebBrowserService {
    @GET("/v3/de5d7a8b-dc42-4f80-9d6d-e0d81a0e73ff/")
    fun getWebBrowsers(): Call<List<WebBrowserDto>>
}
