package com.yapp.bol.data.datasource.file

import com.yapp.bol.data.model.group.response.ImageFileUploadResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileDataSource {

    fun postFileUpload(
        file: File
    ): Flow<ApiResult<ImageFileUploadResponse>>
}
