package com.xl.simplemvvm.ui.mvi

import com.xl.simplemvvm.bean.*

data class MviState(
    val loading: Boolean = false,
    val refresh: Boolean = false,
    val articleBean: ArticleBean? = null,
    val banners: List<BannerImg> = emptyList(),

    var isFirst: Boolean = false,
    var homeInfo: HomeInfo? = null,
    var categoryInfo: List<CategoryInfoItem>? = null,
    var toppics: Toppics? = null,
    var newsInfo: NewsInfo? = null,
    var recommendInfo: RecommendInfo? = null,
    var rankingInfo: RankingInfo? = null,
    var videoRecommendInfo: VideoRecommendInfo? = null,
    var specialDetailInfo: SpecialDetailInfo? = null,
    var searchInfo: SearchInfo? = null
)
