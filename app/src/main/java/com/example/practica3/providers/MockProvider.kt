package com.example.practica3.providers

import com.example.practica3.R
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo

class MockProvider {
    companion object{
        val browserLista = listOf(
            WebBrowserBo(
                "Chrome",
                "Google",
                2008,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.UNKNOWN
            ),
            WebBrowserBo(
                "Firefox",
                "Firefox",
                2010,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.UNKNOWN
            ),
            WebBrowserBo(
                "Edge",
                "Microsoft",
                2018,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.UNKNOWN
            ),
            WebBrowserBo(
                "Safari",
                "Apple",
                2018,
                R.drawable.logo_alten,
                "www.alten.es",
                false,
                BrowserOSEnum.UNKNOWN
            )
        )
    }
}