package com.yapp.bol.data.mapper

import com.yapp.bol.data.model.file.ImageFileUploadResponse
import com.yapp.bol.domain.model.ApiResult

object FileMapper {

    fun ApiResult<ImageFileUploadResponse>.fileUploadToDomain(): ApiResult<String> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.url)
            is ApiResult.Error -> ApiResult.Error(exception)
        }
    }
}
