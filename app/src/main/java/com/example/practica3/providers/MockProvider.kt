package com.example.practica3.providers

import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo

class MockProvider {
    companion object {
        val browserList = listOf(
            WebBrowserBo(
                "Safari",
                "Apple",
                2003,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                false,
                listOf(
                    BrowserOSEnum.iOS, BrowserOSEnum.MacOs
                )
            ), WebBrowserBo(
                "Chrome",
                "Google",
                2008,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                true,
                listOf(
                    BrowserOSEnum.iOS,
                    BrowserOSEnum.MacOs,
                    BrowserOSEnum.Android,
                    BrowserOSEnum.Linux,
                    BrowserOSEnum.Windows,
                    BrowserOSEnum.ChromeOS
                )
            ), WebBrowserBo(
                "Firefox",
                "Mozilla",
                2002,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                true,
                listOf(
                    BrowserOSEnum.iOS,
                    BrowserOSEnum.MacOs,
                    BrowserOSEnum.Android,
                    BrowserOSEnum.Linux,
                    BrowserOSEnum.Windows
                )
            ), WebBrowserBo(
                "Edge",
                "Microsoft",
                2015,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                true,
                listOf(
                    BrowserOSEnum.Android, BrowserOSEnum.Windows
                )
            ), WebBrowserBo(
                "Opera",
                "Opera",
                1995,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                true,
                listOf(
                    BrowserOSEnum.iOS,
                    BrowserOSEnum.MacOs,
                    BrowserOSEnum.Android,
                    BrowserOSEnum.Linux,
                    BrowserOSEnum.Windows
                )
            ), WebBrowserBo(
                "GNU IceCat",
                "Proyecto GNU",
                2008,
                "https://www.alten.com/wp-content/uploads/2019/03/LOGO_Alten_Couleurs_Black.png",
                "https://www.alten.es/",
                false,
                listOf(
                    BrowserOSEnum.Linux,
                )
            )
        )
    }
}