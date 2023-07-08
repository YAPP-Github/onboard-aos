package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.response.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.data.model.group.response.ProfileUploadResponse
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface RemoteDataSource {

    suspend fun login(type: String, token: String): LoginResponse?

    fun checkGroupJoinAccessCode(
        groupId: String,
        accessCode: String,
    ): Flow<ApiResult<CheckGroupJoinByAccessCodeResponse>>

    fun postFileUpload(
        token: String,
        file: File,
    ): Flow<ApiResult<ProfileUploadResponse>>

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

    fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<BaseResponse>>
}
