package com.xl.simplemvvm.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.xl_base.api.Response
import com.xl.xl_base.base.ReduxViewModel
import com.xl.xl_base.tool.ktx.FlowRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MviViewModel @Inject constructor(private var repository: MviRepository) :
    ReduxViewModel<MviState>(MviState()) {

    fun getBanner() {
        viewModelScope.launch {
            val data = repository.getBanners()
            setState {
                copy(banners = data.data!!)
            }
        }
    }

    fun getArtic(page: Int) {
        setState {
            copy(loading = true)
        }
        launchFlow<ArticleBean> {
            loader { repository.getArtic(page) }
            action {
                setState {
                    copy(loading = false, articleBean = it)
                }
            }
        }
    }
}

