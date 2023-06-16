package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.RemoteDataSource
import com.yapp.bol.data.datasource.mock.impl.LoginType.toDomain
import com.yapp.bol.data.model.OAuthApiRequest
import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.model.group.GameApiResponse
import com.yapp.bol.data.model.group.NewGroupApiRequest
import com.yapp.bol.data.model.group.NewGroupApiResponse
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
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val loginApi: LoginApi,
    private val imageFileApi: ImageFileApi,
    private val groupApi: GroupApi,
) : BaseRepository(), RemoteDataSource {

    override suspend fun login(type: String, token: String): OAuthApiResponse? {
        return loginApi.postOAuthApi(OAuthApiRequest(type.toDomain(), token)).body()
    }

    override fun postFileUpload(
        token: String,
        file: File
    ): Flow<ApiResult<FileUploadResponse>> = flow {

        val fileBody = RequestBody.create(MediaType.parse(MEDIA_TYPE_IMAGE), file)
        val filePart = MultipartBody.Part.createFormData(FILE_KEY, file.name, fileBody)
        val purpose = RequestBody.create(MediaType.parse(MEDIA_TYPE_TEXT), GROUP_IMAGE)

        val result = safeApiCall {
            imageFileApi.postFileUpload(token = token.convertRequestToken(), file = filePart, purpose = purpose)
        }
        emit(result)
    }

    override fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        profileImageUrl: String,
        nickname: String
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

    private fun String.convertRequestToken(): String {
        return "Bearer $this"
    }

    companion object {
        const val MEDIA_TYPE_IMAGE = "image/*"
        const val MEDIA_TYPE_TEXT = "text/*"
        const val FILE_KEY = "file"
    }
}
