package com.yapp.bol.data.datasource.group

import com.yapp.bol.data.model.group.GroupDetailResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.model.group.JoinedGroupResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GroupDataSource {

    suspend fun searchGroup(
        name: String,
        page: Int,
        pageSize: Int,
    ): ApiResult<GroupSearchApiResponse>

    fun getJoinedGroup(): Flow<ApiResult<JoinedGroupResponse>>

    fun getGroupDetail(groupId: Long): Flow<ApiResult<GroupDetailResponse>>
}
