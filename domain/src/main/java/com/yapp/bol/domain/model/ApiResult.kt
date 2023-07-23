package com.yapp.bol.domain.model

sealed class ApiResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: ErrorItem) : ApiResult<Nothing>()
}
