package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GetGroupGameListItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.model.JoinedGroupItem
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    suspend fun searchGroup(
        name: String,
        page: Int,
        pageSize: Int,
    ): ApiResult<GroupSearchItem>

    fun getJoinedGroup(): Flow<ApiResult<List<JoinedGroupItem>>>

    fun getGroupDetail(groupId: Long): Flow<ApiResult<GroupDetailItem>>

    fun getGroupGameList(groupId: Int): Flow<ApiResult<GetGroupGameListItem>>
}
