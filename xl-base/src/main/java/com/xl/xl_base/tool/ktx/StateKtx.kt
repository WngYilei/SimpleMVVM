package com.xl.xl_base.tool.ktx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xl.xl_base.api.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlowRequest<T> {
    private lateinit var loader: suspend () -> Flow<Response<T>>
    private var responseResult: ((state: State) -> Unit)? = null
    infix fun loader(block: suspend () -> Flow<Response<T>>) {
        loader = block
    }

    infix fun onResult(block: (State) -> Unit) {
        responseResult = block
    }

    fun launchRequest(scope: CoroutineScope) {
        scope.launch {
            try {
                loader().collect {
                    responseResult?.invoke(State.Loading)
                    if (it.errorCode == 0) {
                        responseResult?.invoke(State.Body(it.data))
                    } else {
                        responseResult?.invoke(State.Error(it.errorMsg))
                    }
                }
            } catch (e: Exception) {
                responseResult?.invoke(State.Error(e.message))
            } finally {
                responseResult?.invoke(State.Complete)
            }
        }
    }
}

inline fun <T> ViewModel.launchFlowRequest(crossinline request: FlowRequest<T>.() -> Unit) {
    FlowRequest<T>().apply(request).launchRequest(viewModelScope)
}