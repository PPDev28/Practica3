package com.example.practica3.retrofit

import com.example.practica3.enums.BrowserOSEnum
import com.example.practica3.models.WebBrowserBo
import com.example.practica3.models.WebBrowserDto
import com.example.practica3.models.Mapper


class WebBrowserDtoMapper : Mapper<WebBrowserDto, WebBrowserBo> {
    override fun map(input: WebBrowserDto): WebBrowserBo {
        val compatibleOS = input.compatible.map { BrowserOSEnum.valueOf(it) }
        return WebBrowserBo(
            browserName = input.name,
            browserCompany = input.company ,
            browserCreationDate = input.year,
            browserImage = input.logo,
            browserWeb = input.web,
            browserMobile = input.mobile,
            compatibleOS = compatibleOS,
        )
    }
}

