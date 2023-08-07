package com.yapp.bol.domain.utils

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.ErrorItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend fun <T : Any> Flow<ApiResult<T>>.doWork(
    isSuccess: (T) -> Unit,
    isError: (exception: ErrorItem) -> Unit? = { null },
) {
    collectLatest {
        when (it) {
            is ApiResult.Success -> {
                isSuccess(it.data)
            }

            is ApiResult.Error -> {
                isError(it.exception)
            }
        }
    }
}
