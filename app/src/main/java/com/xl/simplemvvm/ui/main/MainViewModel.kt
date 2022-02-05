package com.xl.simplemvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.simplemvvm.bean.BannerImg
import com.xl.xl_base.base.ReduxViewModel
import com.xl.xl_base.tool.ktx.State
import com.xl.xl_base.tool.ktx.launchFlowRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: MainRepository) :
    ReduxViewModel<State>(State.Idle) {

    private val _data = MutableLiveData<ArticleBean?>()
    val data: LiveData<ArticleBean?> = _data

    fun getArc(page: Int) {
        viewModelScope.launch {
            _data.value = repository.getArticles(page = page).data
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