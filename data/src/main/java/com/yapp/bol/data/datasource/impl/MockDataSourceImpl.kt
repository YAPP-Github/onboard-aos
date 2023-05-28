package com.yapp.bol.data.datasource.impl

import com.yapp.bol.data.datasource.MockDataSource
import com.yapp.bol.data.model.MockApiRequest
import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.data.model.MockType
import com.yapp.bol.data.remote.LoginApi
import com.yapp.bol.data.utils.BaseRepository
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val oauthApi: LoginApi,
) : BaseRepository(), MockDataSource {
    override suspend fun getKakaoMock(remoteErrorEmitter: RemoteErrorEmitter, token: String): MockApiResponse? {
        return safeApiCall(remoteErrorEmitter) {
            oauthApi.postMockApi(MockApiRequest(MockType.KAKAO, "SUCCESSid")).body()
        }
    }
}
