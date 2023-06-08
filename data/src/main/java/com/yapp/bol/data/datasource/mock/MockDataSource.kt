package com.yapp.bol.data.datasource.mock

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.domain.utils.RemoteErrorEmitter

interface MockDataSource {
    suspend fun login(emitter: RemoteErrorEmitter, type: String, token: String): MockApiResponse?
}
