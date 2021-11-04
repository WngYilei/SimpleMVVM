package com.xl.simplemvvm.ui.main

import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.intent.MainState
import com.xl.simplemvvm.intent.launchFlowRequest
import com.xl.xl_base.base.ReduxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: MainRepository) : ReduxViewModel<MainState>(MainState.Idle) {

    fun getArc2(page: Int) {
        launchFlowRequest<ArticleBean> {
            loader {
                repository.getArticles2(page)
            }
            onResult {
                _state.value = it
            }
        }
    }

}