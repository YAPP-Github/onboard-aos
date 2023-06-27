package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.GameApiResponse
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.MemberValidApiResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteDataSource {

    suspend fun login(type: String, token: String): OAuthApiResponse?

    fun postFileUpload(
        token: String,
        file: File
    ): Flow<ApiResult<FileUploadResponse>>

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        profileImageUrl: String,
        nickname: String,
    ): Flow<ApiResult<NewGroupApiResponse>>

    fun getGameList(groupId: Int): Flow<ApiResult<GameApiResponse>>

    fun getValidateNickName(
        groupId: Int,
        nickname: String,
    ): Flow<ApiResult<MemberValidApiResponse>>

    fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String? = null,
        nickname: String? = null,
    ): Flow<ApiResult<MemberListResponse>>
}
