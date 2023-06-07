package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.mock.MockDataSource
import com.yapp.bol.data.mapper.MapperToDomain.toDomain
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.repository.MockRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import java.io.File
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val mockDataSource: MockDataSource,
) : MockRepository {
    override suspend fun login(
        emitter: RemoteErrorEmitter,
        type: String,
        token: String,
    ): LoginItem? = mockDataSource.login(emitter = emitter, type = type, token = token).toDomain()

    override suspend fun postFileUpload(remoteErrorEmitter: RemoteErrorEmitter, token: String, file: File): String {
        return mockDataSource.postFileUpload(remoteErrorEmitter,token,file)?.url ?: "Null !!!"
    }

}
