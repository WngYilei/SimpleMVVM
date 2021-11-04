package com.xl.simplemvvm.intent

import com.xl.simplemvvm.bean.BannerImg

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    object Complete : MainState()
    data class Error(val error: String?) : MainState()

    data class Banners(val banners: List<BannerImg>?) : MainState()
    data class Body(val data: Any?) : MainState()
    companion object {
       val data:MainState = Idle
    }
}

