package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.RemoteDataSource
import com.yapp.bol.data.datasource.mock.impl.LoginType.toDomain
import com.yapp.bol.data.model.base.BaseResponse
import com.yapp.bol.data.model.group.JoinGroupApiRequest
import com.yapp.bol.data.model.group.request.CheckGroupJonByAccessCodeRequest
import com.yapp.bol.data.model.login.LoginRequest
import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.data.model.group.response.ProfileUploadResponse
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.request.NewGroupApiRequest
import com.yapp.bol.data.model.group.response.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.data.remote.GroupApi
import com.yapp.bol.data.remote.LoginApi
import com.yapp.bol.data.utils.Image.GROUP_IMAGE
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URLConnection
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val groupApi: GroupApi,
) : BaseRepository(), RemoteDataSource {

    override suspend fun login(type: String, token: String): LoginResponse? {
        return loginApi.postOAuthApi(LoginRequest(type.toDomain(), token)).body()
    }

    override fun checkGroupJoinAccessCode(
        groupId: String,
        accessCode: String,
    ): Flow<ApiResult<CheckGroupJoinByAccessCodeResponse>> {
        return flow {
            val result = safeApiCall {
                groupApi.checkGroupJoinAccessCode(groupId, CheckGroupJonByAccessCodeRequest(accessCode))

            }
            emit(result)
        }
    }

    override fun postFileUpload(
        token: String,
        file: File,
    ): Flow<ApiResult<ProfileUploadResponse>> = flow {
        val fileBody = RequestBody.create(MediaType.parse(getMimeType(file.name)), file)
        val filePart = MultipartBody.Part.createFormData(FILE_KEY, file.name, fileBody)
        val purpose = RequestBody.create(MediaType.parse(getMimeType(file.name)), GROUP_IMAGE)

        val result = safeApiCall {
            groupApi.postProfileUpload(token = token, file = filePart, purpose = purpose)
        }
        emit(result)
    }

    override fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        profileImageUrl: String,
        nickname: String,
    ): Flow<ApiResult<NewGroupApiResponse>> = flow {
        val result = safeApiCall {
            groupApi.postOAuthApi(NewGroupApiRequest(name, description, organization, profileImageUrl, nickname))
        }
        emit(result)
    }

    override fun getGameList(groupId: Int): Flow<ApiResult<GameApiResponse>> = flow {
        val result = safeApiCall { groupApi.getGameList(groupId) }
        emit(result)
    }

    override fun getValidateNickName(
        groupId: Int,
        nickname: String,
    ): Flow<ApiResult<MemberValidApiResponse>> = flow {
        val result = safeApiCall { groupApi.getValidateNickName(groupId, nickname) }
        emit(result)
    }

    private fun getMimeType(fileName: String): String {
        return URLConnection.guessContentTypeFromName(fileName)
    }

    override fun joinGroup(
        groupId: String,
        accessCode: String,
        nickname: String,
    ): Flow<ApiResult<BaseResponse>> = flow {
        val result = safeApiCall { groupApi.joinGroup(groupId, JoinGroupApiRequest(nickname, accessCode)) }
        emit(result)
    }

    companion object {
        const val FILE_KEY = "file"
    }
}
