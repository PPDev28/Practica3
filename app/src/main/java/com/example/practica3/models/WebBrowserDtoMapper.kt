package com.example.practica3.models

import com.example.practica3.enums.BrowserOSEnum


class WebBrowserDtoMapper : Mapper<WebBrowserDto, WebBrowserBo> {
    override fun map(input: WebBrowserDto): WebBrowserBo {
        val dtoCompatibleOS = input.compatible.map { BrowserOSEnum.valueOf(it) }
        return WebBrowserBo(
            browserName = input.name,
            browserCompany = input.company ,
            browserCreationDate = input.year,
            browserImage = input.logo,
            browserWeb = input.web,
            browserMobile = input.mobile,
            compatibleOS = dtoCompatibleOS,
        )
    }
}

