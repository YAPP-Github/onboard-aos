package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import java.io.File

interface RemoteDataSource {

    suspend fun login(type: String, token: String): OAuthApiResponse?

    fun postFileUpload(
        token: String,
        file: File,
    ): Flow<ApiResult<FileUploadResponse>>

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        profileImageUrl: String,
        nickname: String,
    ): Flow<ApiResult<NewGroupApiResponse>>

    fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<BaseResponse>>
}
