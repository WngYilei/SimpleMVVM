package com.xl.xl_base.tool.ktx

import androidx.lifecycle.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HandlerRequest {
    private var loading: (() -> Unit)? = null
    private var complete: (() -> Unit)? = null
    private var result: ((Any?) -> Unit)? = null
    private var onError: ((String) -> Unit)? = null

    infix fun onResult(block: (Any?) -> Unit) {
        result = block
    }

    infix fun loading(block: () -> Unit) {
        loading = block
    }

    infix fun complete(block: () -> Unit) {
        complete = block
    }

    infix fun onError(block: (String) -> Unit) {
        onError = block
    }


    fun launchRequest(state: State) {
        when (state) {
            is State.Loading -> {
                loading?.invoke()
            }
            is State.Error -> {
                onError?.invoke(state.error!!)
            }
            is State.Complete -> {
                complete?.invoke()
            }
            is State.Body -> {
                result?.invoke(state.data)
            }
            else -> {
            }
        }
    }
}


inline fun StateFlow<State>.collectHandlerFlow(
    lifecycleOwner: LifecycleOwner,
    crossinline request: HandlerRequest.() -> Unit,
) =
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect {
                run {
                    HandlerRequest().apply(request).launchRequest(it)
                }
            }
        }
    }
