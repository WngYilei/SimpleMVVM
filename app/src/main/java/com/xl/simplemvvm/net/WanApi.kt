package com.xl.simplemvvm.net


import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.xl_base.api.Response
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface WanApi {
    @GET("/banner/json")
    suspend fun getBanner(): Response<List<BannerImg>>

    @GET("/banner/json")
    suspend fun getBannerFlow(): Response<List<BannerImg>>

    @GET("/banner/json")
    suspend fun getUser(): Response<String>

    @GET("/article/list/{pageNo}/json")
    suspend fun getArticle(@Path("pageNo") pageNo: Int): Response<ArticleBean>
}