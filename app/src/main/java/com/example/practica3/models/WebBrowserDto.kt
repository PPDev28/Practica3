package com.example.practica3.models

import com.example.practica3.enums.BrowserOSEnum
import com.google.gson.annotations.SerializedName

data class WebBrowserDto(
    val id: Long,
    @SerializedName(value = "name")
    var browserName: String,
    @SerializedName(value = "company")
    val browserCompany: String,
    @SerializedName(value = "year")
    val browserCreationDate: Int,
    @SerializedName(value = "logo")
    val browserImage: String,
    @SerializedName(value = "web")
    val browserWeb: String,
    @SerializedName(value = "mobile")
    val browserMobile: Boolean,
    @SerializedName(value = "compatible")
    val compatibleOS: List<String>
)

fun WebBrowserDto.toBO() = WebBrowserBo(
    browserName = browserName,
    browserCompany = browserCompany,
    browserCreationDate = browserCreationDate,
    browserImage = browserImage,
    browserWeb = browserWeb,
    browserMobile = browserMobile,
    compatibleOS = compatibleOS.map {
        BrowserOSEnum.valueOf(it)
    }
)

