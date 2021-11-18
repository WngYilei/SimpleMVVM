package com.xl.simplemvvm.ui.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.xl.simplemvvm.bean.BannerImg
import com.xl.simplemvvm.ui.mvi.MviState
import com.xl.xl_base.base.ReduxViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ComposeViewModel @Inject constructor(private var repository: ComposeRepository) :
    ReduxViewModel<MviState>(MviState()) {

    fun getBanner() {
        launchFlow<List<BannerImg>> {
            loader { repository.getBanners() }
            action {
                setState {
                    copy(banners = it!!)
                }
            }
        }
    }


}