package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.user.group.MyGroupItem
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getMyGroupList(): Flow<ApiResult<List<MyGroupItem>>>
}
