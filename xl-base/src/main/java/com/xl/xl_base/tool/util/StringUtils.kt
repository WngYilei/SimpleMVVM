package com.xl.xl_base.tool.util

object StringUtils {

    fun getUrl(url: String): HashMap<String, String> {
        val list = url.split("?")
        val map = HashMap<String, String>()
        val var1 = list[1].split("&")
        val data = var1[0].split("=")[1]
        val num = var1[1].split("=")[1]
        map["date"] = data
        map["num"] = num
        return map
    }
}