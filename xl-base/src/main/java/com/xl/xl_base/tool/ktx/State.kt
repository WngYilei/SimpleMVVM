package com.xl.xl_base.tool.ktx
sealed class State {
    object Idle : State()
    object Loading : State()
    object Complete : State()
    data class Error(val error: String?) : State()
    data class Body(val data: Any?) : State()
}

