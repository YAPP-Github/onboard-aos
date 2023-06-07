package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import java.io.File

interface MockRepository {
    suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiItem?

    suspend fun postFileUpload(remoteErrorEmitter: RemoteErrorEmitter, token: String, file: File): String
}
