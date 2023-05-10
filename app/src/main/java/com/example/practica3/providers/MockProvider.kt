package com.example.practica3.providers

import com.example.practica3.R
import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo

class MockProvider {
    companion object{
        val browserList = listOf(
            WebBrowserBo(
                "Safari",
                "Apple",
                2003,
                R.drawable.logo_alten,
                "www.alten.es",
                false,
                BrowserOSEnum.ChromeOS
            ),
            WebBrowserBo(
                "Chrome",
                "Google",
                2008,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.ChromeOS
            ),
            WebBrowserBo(
                "Firefox",
                "Mozilla",
                2002,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.ChromeOS
            ),
            WebBrowserBo(
                "Edge",
                "Microsoft",
                2015,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.ChromeOS
            ),
            WebBrowserBo(
                "Opera",
                "Opera",
                1995,
                R.drawable.logo_alten,
                "www.alten.es",
                true,
                BrowserOSEnum.ChromeOS
            )
        )
    }
}