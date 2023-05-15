package com.example.practica3.retrofit

import com.example.practica3.models.WebBrowserDto
import retrofit2.Call
import retrofit2.http.GET


interface WebBrowserService {
    @GET("/v3/c01f985f-a1d5-4dbb-98e7-6002db06447c/")
    fun getWebBrowsers(): Call<List<WebBrowserDto>>
}
