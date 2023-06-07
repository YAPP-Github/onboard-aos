package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import java.io.File

interface MockRepository {
    suspend fun login(emitter: RemoteErrorEmitter, type: String, token: String): LoginItem?

    suspend fun postFileUpload(remoteErrorEmitter: RemoteErrorEmitter, token: String, file: File): String

}
