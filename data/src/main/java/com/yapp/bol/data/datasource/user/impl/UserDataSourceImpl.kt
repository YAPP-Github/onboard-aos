package com.yapp.bol.data.datasource.user.impl

import com.yapp.bol.data.datasource.user.UserDataSource
import com.yapp.bol.data.model.user.GetMyGroupListResponse
import com.yapp.bol.data.remote.UserApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi,
) : BaseRepository(), UserDataSource {

    override suspend fun getMyGroupList(): Flow<ApiResult<GetMyGroupListResponse>> {
        return flow {
            emit(safeApiCall { userApi.getMyGroupList() })
        }
    }
}
