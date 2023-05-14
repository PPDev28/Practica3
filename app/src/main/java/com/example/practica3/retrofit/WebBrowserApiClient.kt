package com.example.practica3.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebBrowserApiClient {
    private const val BASE_URL = "https://run.mocky.io/v3/59bc6ad0-efef-4a44-8756-de4ca5bee87c/"
    private var webBrowserService: WebBrowserService? = null

    fun getWebBrowserService(): WebBrowserService? {
        if (webBrowserService == null) {
            // Configuramos un interceptor para ver las trazas de la llamada al servicio GET
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // Creamos un cliente OkHttp y le asignamos el interceptor
            val httpClientBuilder = OkHttpClient.Builder().apply {
                addInterceptor(loggingInterceptor)
            }

            // Creamos una instancia de Retrofit y le asignamos el cliente OkHttp
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build()

            // Creamos una instancia de la interfaz WebBrowserService
            webBrowserService = retrofit.create(WebBrowserService::class.java)
        }
        return webBrowserService
    }
}
