package com.xl.simplemvvm.ui.mvi

import com.xl.simplemvvm.net.WanApi
import com.xl.xl_base.base.BaseRepository
import javax.inject.Inject

class MviRepository @Inject constructor(private val service: WanApi) : BaseRepository() {

    suspend fun getBanners() = service.getBanner()


    suspend fun getArtic(page: Int) = service.getArticle(pageNo = page)


    suspend fun getHome(index: String) = service.getHomeData(index)

    suspend fun getNextHome(date: String) = service.getNextHomePage(date)

}