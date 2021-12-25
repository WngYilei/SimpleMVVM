package com.xl.xl_base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xl.xl_base.api.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class ReduxViewModel<S>(initialState: S) : ViewModel() {

    val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

    fun currentState(): S = _state.value

    private val stateMutex = Mutex()

    protected fun setState(reducer: S.() -> S) {
        _state.value = reducer(_state.value)
    }

    private suspend fun withState(block: (S) -> Unit) {
        stateMutex.withLock {
            block(_state.value)
        }
    }

    private val _error = MutableStateFlow("")
    private val error: StateFlow<String>
        get() = _error


    private val errMeth = fun(reducer: String.() -> String) {
        _error.value = reducer(error.value)
    }

    fun <T> launchFlow(request: FlowRequest<T>.() -> Unit) {
        FlowRequest<T>().apply(request).launchRequest(viewModelScope, errMeth)
    }
}

class FlowRequest<T> {
    private lateinit var loader: suspend () -> Response<T>
    private var action: ((T?) -> Unit)? = null
    infix fun loader(block: suspend () -> Response<T>) {
        loader = block
    }

    infix fun action(block: (T?) -> Unit) {
        action = block
    }

    fun launchRequest(
        scope: CoroutineScope,
        setError: (reducer: String.() -> String) -> Unit
    ) {
        scope.launch {
            try {
                val data = loader.invoke()
                if (data.errorCode != 0) {
                    setError {
                        data.errorMsg
                    }
                } else {
                    action?.invoke(data.data)
                }

            } catch (e: Exception) {
                setError {
                    e.toString()
                }
            }
        }
    }
}