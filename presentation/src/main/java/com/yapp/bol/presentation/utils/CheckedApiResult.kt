package com.yapp.bol.presentation.utils

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.ErrorItem

fun <T : Any> checkedApiResult(
    apiResult: ApiResult<T>,
    success: (T) -> Unit,
    error: ((ErrorItem) -> Unit)? = null,
) {
    when (apiResult) {
        is ApiResult.Success -> success(apiResult.data)
        is ApiResult.Error -> error?.invoke(apiResult.exception)
    }
}
