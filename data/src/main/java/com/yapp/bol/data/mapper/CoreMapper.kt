package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.base.ErrorResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.ErrorItem

object CoreMapper {

    fun ApiResult<ErrorResponse>.mapperToBaseItem(): ApiResult<ErrorItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(ErrorItem(data.code, data.message))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}
