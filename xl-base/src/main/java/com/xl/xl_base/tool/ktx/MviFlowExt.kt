package com.xl.simplemvvm.ui.mvi

import androidx.lifecycle.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> StateFlow<T>.collectHandlerFlow(
    lifecycleOwner: LifecycleOwner,
    crossinline action: (T) -> Unit,
) =
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) { collect { T -> action(T) } }
    }
