package com.example.practica3.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebBrowserApiClient {
    private const val BASE_URL = "https://run.mocky.io/v3/de5d7a8b-dc42-4f80-9d6d-e0d81a0e73ff/"
    private var webBrowserService: WebBrowserService? = null

    fun getWebBrowserService(): WebBrowserService? {
        if (webBrowserService == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val httpClientBuilder = OkHttpClient.Builder().apply {
                addInterceptor(loggingInterceptor)
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

            webBrowserService = retrofit.create(WebBrowserService::class.java)
        }
        return webBrowserService
    }
}