package com.yapp.bol.presentation.utils

import com.yapp.bol.domain.model.ApiResult

fun <T : Any> checkedApiResult(
    apiResult: ApiResult<T>,
    success: (T) -> Unit,
    error: (Throwable) -> Unit,
) {
    when (apiResult) {
        is ApiResult.Success -> success(apiResult.data)
        is ApiResult.Error -> error(apiResult.exception)
    }
}
