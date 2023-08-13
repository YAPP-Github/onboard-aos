package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.BaseItem
import com.yapp.bol.domain.model.ErrorItem

object CoreMapper {

    fun ApiResult<BaseResponse>.mapperToBaseItem(): ApiResult<ErrorItem> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(ErrorItem(data.code, data.message))
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}
