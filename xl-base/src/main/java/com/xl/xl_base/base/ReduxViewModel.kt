package com.xl.xl_base.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

abstract class ReduxViewModel<S>(initialState: S) : ViewModel() {

    val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

}