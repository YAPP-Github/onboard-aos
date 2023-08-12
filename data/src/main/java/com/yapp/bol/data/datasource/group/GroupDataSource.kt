package com.yapp.bol.data.datasource.group

import com.yapp.bol.data.model.group.GetGroupGameListResponse
import com.yapp.bol.data.model.group.response.GroupDetailResponse
import com.yapp.bol.data.model.group.response.JoinedGroupResponse
import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
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

    fun getGroupGameList(groupId: Int): Flow<ApiResult<GetGroupGameListResponse>>
}
