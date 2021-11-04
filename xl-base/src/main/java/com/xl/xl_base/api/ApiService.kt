package com.xl.xl_base.api

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Api服务
 */
class ApiService constructor(baseUrl: String) {
    val retrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        val clientBuild = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(30, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequestsPerHost = 10
            })

        clientBuild.addInterceptor(HttpLoggingInterceptor { }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuild.build())
            .build()
    }
}