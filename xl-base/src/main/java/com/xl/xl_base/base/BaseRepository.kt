package com.xl.xl_base.base

import com.xl.xl_base.api.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {

    suspend fun <T> handlerFlowRequest(response: Response<T>) = flow {
        flow {
            emit(response)
        }.catch {
            emit(Response(errorMsg = "网络不给力", errorCode = -1, data = null))
        }.flowOn(Dispatchers.IO).collect {
            emit(it)
        }
    }

}