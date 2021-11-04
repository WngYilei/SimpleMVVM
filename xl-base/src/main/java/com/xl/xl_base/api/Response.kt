package com.xl.xl_base.api


data class Response<T> (
    val errorCode: Int,
    val errorMsg: String ="",
    val data: T? = null,
)