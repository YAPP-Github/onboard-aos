package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.MockDataSource
import com.yapp.bol.data.model.LoginType
import com.yapp.bol.data.model.MockApiRequest
import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.data.utils.Image.GROUP_IMAGE
import com.yapp.bol.data.remote.ImageFileApi
import com.yapp.bol.data.remote.LoginApi
import com.yapp.bol.data.utils.BaseRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


class MockDataSourceImpl @Inject constructor(
    private val oauthApi: LoginApi,
    private val imageFileApi: ImageFileApi,
) : BaseRepository(), MockDataSource {
    override suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiResponse? {
        return safeApiCall(remoteErrorEmitter) {
            oauthApi.postMockApi(MockApiRequest(LoginType.KAKAO_ACCESS_TOKEN, token)).body()
        }
    }

    override suspend fun postFileUpload(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        file: File
    ): FileUploadResponse? {

        val fileBody = RequestBody.create(MediaType.parse(MEDIA_TYPE_IMAGE),file)
        val filePart = MultipartBody.Part.createFormData(FILE_KEY,file.name,fileBody)
        val purpose = RequestBody.create(MediaType.parse(MEDIA_TYPE_TEXT), GROUP_IMAGE)

       return safeApiCall(remoteErrorEmitter) {
           imageFileApi.postFileUpload(
               token = token.convertRequestToken(),
               file = filePart,
               purpose = purpose
           ).body()
       }
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
