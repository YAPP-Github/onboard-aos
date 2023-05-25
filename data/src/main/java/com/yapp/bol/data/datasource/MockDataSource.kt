package com.yapp.bol.data.datasource

import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.domain.utils.RemoteErrorEmitter

interface MockDataSource {
    suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiResponse?
}
