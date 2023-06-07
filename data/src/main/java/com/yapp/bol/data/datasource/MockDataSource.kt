package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.data.model.file_upload.FileUploadResponse
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import java.io.File

interface MockDataSource {
    suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiResponse?

    suspend fun postFileUpload(
        remoteErrorEmitter: RemoteErrorEmitter,
        token: String,
        file: File
    ): FileUploadResponse?
}
