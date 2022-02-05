package com.xl.simplemvvm.ui.mvi

import androidx.lifecycle.viewModelScope
import com.xl.simplemvvm.bean.ArticleBean
import com.xl.xl_base.base.ReduxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MviViewModel @Inject constructor(private var repository: MviRepository) :
    ReduxViewModel<MviState>(MviState()) {

    private val pendingActions = Channel<ViewEvent>(Channel.BUFFERED)

    init {
        viewModelScope.launch {
            pendingActions.consumeAsFlow().collect { action ->
                when (action) {
                    ViewEvent.Refresh -> getHome(0)
                }
            }
        }
    }

    fun submitAction(action: ViewEvent) {
        viewModelScope.launch {
            if (!pendingActions.isClosedForReceive) {
                pendingActions.send(action)
            }
        }
    }



    fun getHome(index: Int) {
        setState {
            copy(loading = true, refresh = true, homeInfo = null)
        }
        viewModelScope.launch {
            setState { copy(loading = true) }
            val data = repository.getHome(index.toString())
            setState {
                copy(loading = true, refresh = true, homeInfo = data)
            }
        }
    }

    fun getNextHome(date: String) {
        setState {
            copy(loading = true, homeInfo = null)
        }
        viewModelScope.launch {
            setState { copy(loading = true) }
            val data = repository.getNextHome(date)
            setState {
                copy(loading = true, refresh = false, homeInfo = data)
            }
        }
    }



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

