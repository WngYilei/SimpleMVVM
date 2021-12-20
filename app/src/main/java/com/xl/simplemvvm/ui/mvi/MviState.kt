package com.xl.simplemvvm.ui.mvi

import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg

data class MviState(
    val loading: Boolean = false,
    val refresh: Boolean = false,
    val articleBean: ArticleBean? = null,
    val banners: List<BannerImg> = emptyList(),
)
