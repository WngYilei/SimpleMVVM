package com.xl.xl_base.api

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

const val ERROR_NET = -1000

class Request<T> {

    private lateinit var loader: suspend () -> Response<T>
    private var responseResult: ((T) -> Unit)? = null
    private var responseError: ((Response<T>) -> Unit)? = null
    private var responseComplete: (() -> Unit)? = null
    infix fun loader(block: suspend () -> Response<T>) {
        loader = block
    }

    infix fun onResult(block: (T) -> Unit) {
        responseResult = block
    }

    infix fun onError(block: (Response<T>) -> Unit) {
        responseError = block
    }

    infix fun onComplete(block: () -> Unit) {
        responseComplete = block
    }

    fun launchRequest(scope: CoroutineScope) {
        scope.launch {
            try {
                val response = withContext(IO) {
                    loader()
                }
                response.data?.let {
                    responseResult?.invoke(response.data)
                }
            } catch (e: Exception) {
                responseError?.invoke(Response(ERROR_NET, errorMsg = e.message.toString()))
            } finally {
                responseComplete?.invoke()
            }
        }
    }
}

inline fun <T> ViewModel.launchRequest(crossinline request: Request<T>.() -> Unit) {
    Request<T>().apply(request).launchRequest(viewModelScope)
}

inline fun <T> FragmentActivity.launchRequest(crossinline request: Request<T>.() -> Unit) {
    Request<T>().apply(request).launchRequest(lifecycleScope)
}