package com.yapp.bol.data.datasource.mock.impl

import com.yapp.bol.data.datasource.mock.MockDataSource
import com.yapp.bol.data.datasource.mock.impl.LoginType.toDomain
import com.yapp.bol.data.model.MockApiRequest
import com.yapp.bol.data.model.MockApiResponse
import com.yapp.bol.data.remote.LoginApi
import com.yapp.bol.data.utils.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val api: LoginApi,
) : BaseRepository(), MockDataSource {

    override suspend fun login(
        emitter: RemoteErrorEmitter,
        type: String,
        token: String,
    ): MockApiResponse? {
        // TODO : 경민님 작업사항. 체크 필요함.
        val temp = safeApiCall {
            api.postMockApi(MockApiRequest(type.toDomain(), token))
        }
        return if (temp is ApiResult.Success) temp.data else {
            throw Exception()
        }
    }
}