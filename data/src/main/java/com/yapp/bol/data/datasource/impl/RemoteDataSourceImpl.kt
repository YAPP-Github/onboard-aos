package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.RemoteDataSource
import com.yapp.bol.data.model.LoginType
import com.yapp.bol.data.model.MockApiRequest
import com.yapp.bol.data.model.OAuthApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
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
    private val oauthApi: LoginApi,
    private val imageFileApi: ImageFileApi,
    private val groupApi: GroupApi,
) : BaseRepository(), RemoteDataSource {
    override fun getKakaoMock(token: String): Flow<ApiResult<OAuthApiResponse>> = flow {
        val result = safeApiCall {
            oauthApi.postMockApi(MockApiRequest(LoginType.KAKAO_ACCESS_TOKEN, token))
        }
        emit(result)
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

    private fun String.convertRequestToken(): String {
        return "Bearer $this"
    }

    companion object {
        const val MEDIA_TYPE_IMAGE = "image/*"
        const val MEDIA_TYPE_TEXT = "text/*"
        const val FILE_KEY = "file"
    }
}
