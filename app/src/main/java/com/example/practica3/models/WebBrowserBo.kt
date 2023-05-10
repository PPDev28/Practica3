package com.example.practica3.models

import com.example.practica3.constants.*
import com.example.practica3.enums.BrowserOSEnum

data class WebBrowserBo(
    val browserName: String,
    val browserCompany: String,
    val browserCreationDate: Int,
    val browserImage: Int,
    val browserWeb: String,
    val browserMobile: Boolean,
    val browserOS: BrowserOSEnum,
){
    val browserInfo: String
        get() {
            return when (browserOS){
                BrowserOSEnum.WINDOWS -> WINDOWS
                BrowserOSEnum.MAC_OS -> MAC_OS
                BrowserOSEnum.LINUX -> LINUX
                BrowserOSEnum.ANDROID -> ANDROID
                BrowserOSEnum.IOS -> IOS
                BrowserOSEnum.ChromeOS -> CHROME_OS
            }
        }
}