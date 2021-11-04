package com.xl.simplemvvm.intent
sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    object Complete : MainState()
    data class Error(val error: String?) : MainState()
    data class Body(val data: Any?) : MainState()
}

