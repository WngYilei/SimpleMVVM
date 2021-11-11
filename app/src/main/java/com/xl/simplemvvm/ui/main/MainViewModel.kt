package com.xl.simplemvvm.ui.main

import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.xl_base.base.ReduxViewModel
import com.xl.xl_base.tool.ktx.State
import com.xl.xl_base.tool.ktx.launchFlowRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: MainRepository) :
    ReduxViewModel<State>(State.Idle) {

    fun getArc(page: Int) {
        launchFlowRequest<ArticleBean> {
            loader {
                repository.getArticles2(page)
            }
            onResult {
                _state.value = it
            }
        }
    }

    fun getBanner() {
        launchFlowRequest<List<BannerImg>> {
            loader {
                repository.getBannersFlow()
            }
            onResult {
                _state.value = it
            }
        }
    }

}