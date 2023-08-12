package com.yapp.bol.data.datasource.file

import com.yapp.bol.data.datasource.impl.RemoteDataSourceImpl
import com.yapp.bol.data.model.group.response.ImageFileUploadResponse
import com.yapp.bol.data.remote.FileApi
import com.yapp.bol.data.remote.TermsApi
import com.yapp.bol.data.utils.Image
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

class FileDataSourceImpl @Inject constructor(
    private val fileApi: FileApi,
) : BaseRepository(), FileDataSource {

    override fun postFileUpload(file: File): Flow<ApiResult<ImageFileUploadResponse>> = flow {
        val fileBody = RequestBody.create(MediaType.parse(getMimeType(file.name)), file)
        val filePart = MultipartBody.Part.createFormData(RemoteDataSourceImpl.FILE_KEY, file.name, fileBody)
        val purpose = RequestBody.create(MediaType.parse(getMimeType(file.name)), Image.GROUP_IMAGE)

        val result = safeApiCall {
            fileApi.postFileUpload(file = filePart, purpose = purpose)
        }
        emit(result)
    }

    private fun getMimeType(fileName: String): String {
        return URLConnection.guessContentTypeFromName(fileName)
    }
}
