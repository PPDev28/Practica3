package com.example.practica3.models

import com.example.practica3.enums.BrowserOSEnum

data class WebBrowserBo(
    val browserName: String,
    val browserCompany: String,
    val browserCreationDate: Int,
    val browserImage: Int,
    val browserWeb: String,
    val browserMobile: Boolean,
    val browserOS: BrowserOSEnum,
) {

}