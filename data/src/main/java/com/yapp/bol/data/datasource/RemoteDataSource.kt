package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteDataSource {
    fun getKakaoMock(token: String): Flow<ApiResult<OAuthApiResponse>>

    fun postFileUpload(
        token: String,
        file: File
    ): Flow<ApiResult<FileUploadResponse>>

}
