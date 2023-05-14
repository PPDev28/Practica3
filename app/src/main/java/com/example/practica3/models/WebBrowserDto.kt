package com.example.practica3.models

data class WebBrowserDto(
    val id: Long,
    var name: String,
    val company: String,
    val year: Int,
    val logo: String,
    val web: String,
    val mobile: Boolean,
    val compatible: List<String>
)
