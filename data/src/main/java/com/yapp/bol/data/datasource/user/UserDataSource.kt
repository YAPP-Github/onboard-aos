package com.yapp.bol.data.datasource.user

import com.yapp.bol.data.model.user.GetMyGroupListResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface UserDataSource {

    suspend fun getMyGroupList(): Flow<ApiResult<GetMyGroupListResponse>>
}
