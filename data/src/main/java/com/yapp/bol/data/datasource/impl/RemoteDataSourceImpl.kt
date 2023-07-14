package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.mock.impl.LoginType.toDomain
import com.yapp.bol.data.model.group.GuestAddApiRequest
import com.yapp.bol.data.model.group.MemberListResponse
import com.yapp.bol.data.model.group.request.NewGroupApiRequest
import com.yapp.bol.data.model.group.response.GameApiResponse
import com.yapp.bol.data.model.group.response.MemberValidApiResponse
import com.yapp.bol.data.model.group.response.NewGroupApiResponse
import com.yapp.bol.data.model.group.response.ImageFileUploadResponse
import com.yapp.bol.data.model.login.TermsResponse
import com.yapp.bol.data.model.login.LoginRequest
import com.yapp.bol.data.model.login.LoginResponse
import com.yapp.bol.data.model.login.OnBoardResponse
import com.yapp.bol.data.model.login.TermsRequest
import com.yapp.bol.data.remote.GroupApi
import com.yapp.bol.data.remote.ImageFileApi
import com.yapp.bol.data.remote.LoginApi
import com.yapp.bol.data.utils.BaseRepository
import com.yapp.bol.data.utils.Image.GROUP_IMAGE
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
    private val imageApi: ImageFileApi,
) : BaseRepository(), RemoteDataSource {

    override suspend fun login(type: String, token: String): LoginResponse? {
        return loginApi.postOAuthApi(LoginRequest(type.toDomain(), token)).body()
    }

    override fun postFileUpload(file: File): Flow<ApiResult<ImageFileUploadResponse>> = flow {
        val fileBody = RequestBody.create(MediaType.parse(getMimeType(file.name)), file)
        val filePart = MultipartBody.Part.createFormData(FILE_KEY, file.name, fileBody)
        val purpose = RequestBody.create(MediaType.parse(getMimeType(file.name)), GROUP_IMAGE)

        val result = safeApiCall {
            imageApi.postFileUpload(file = filePart, purpose = purpose)
        }
        emit(result)
    }

    override fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        imageUrl: String,
        nickname: String
    ): Flow<ApiResult<NewGroupApiResponse>> = flow {
        val result = safeApiCall {
            groupApi.postOAuthApi(NewGroupApiRequest(name, description, organization, imageUrl, nickname))
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

    override fun getMemberList(
        groupId: Int,
        pageSize: Int,
        cursor: String?,
        nickname: String?,
    ): Flow<ApiResult<MemberListResponse>> = flow {
        val result = safeApiCall { groupApi.getMemberList(groupId, pageSize, cursor, nickname) }
        emit(result)
    }

    override suspend fun postGuestMember(groupId: Int, nickname: String) {
        groupApi.postGuestMember(groupId, GuestAddApiRequest(nickname))
    }

    override fun geTerms(): Flow<ApiResult<TermsResponse>> = flow {
        val result = safeApiCall { loginApi.getTerms() }
        emit(result)
    }

    override suspend fun postTerms(termsRequest: TermsRequest) {
        loginApi.postTerms(termsRequest)
    }

    override fun getOnBoard(): Flow<ApiResult<OnBoardResponse>> = flow {
        val result = safeApiCall { loginApi.getOnboard() }
        emit(result)
    }

    private fun getMimeType(fileName: String): String {
        return URLConnection.guessContentTypeFromName(fileName)
    }

    companion object {
        const val FILE_KEY = "file"
    }
}
