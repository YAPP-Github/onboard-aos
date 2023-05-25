package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.utils.RemoteErrorEmitter

interface MockRepository {
    suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiItem?
}
