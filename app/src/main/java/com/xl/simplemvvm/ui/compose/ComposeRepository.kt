package com.xl.simplemvvm.ui.compose

import com.xl.simplemvvm.net.WanApi
import com.xl.xl_base.base.BaseRepository
import javax.inject.Inject

class ComposeRepository @Inject constructor(private val service: WanApi) : BaseRepository() {

    suspend fun getBanners() = service.getBanner()

    suspend fun getArticles(page: Int) = service.getArticle(page)

    suspend fun getBannersFlow() = handlerFlowRequest(service.getBannerFlow())

    suspend fun getArticles2(page: Int) = handlerFlowRequest(service.getArticle(page))


}