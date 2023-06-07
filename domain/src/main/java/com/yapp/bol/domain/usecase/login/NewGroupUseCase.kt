package com.yapp.bol.domain.usecase.login

import com.yapp.bol.domain.repository.MockRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import java.io.File
import javax.inject.Inject

class NewGroupUseCase @Inject constructor(
    private val repository: MockRepository
) {
    suspend fun postFileUpload(remoteErrorEmitter: RemoteErrorEmitter, token: String, file: File): String {
        return repository.postFileUpload(remoteErrorEmitter,token,file)
    }
}
