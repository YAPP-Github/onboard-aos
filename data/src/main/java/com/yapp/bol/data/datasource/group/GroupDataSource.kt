package com.yapp.bol.data.datasource.group

import com.yapp.bol.data.model.group.CheckGroupJoinByAccessCodeResponse
import com.yapp.bol.data.model.group.GroupDetailResponse
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.model.group.NewGroupApiResponse
import com.yapp.bol.data.model.group.RandomImageResponse
import com.yapp.bol.data.model.group.UserRankApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GroupDataSource {

    fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        imageUrl: String,
        nickname: String,
    ): Flow<ApiResult<NewGroupApiResponse>>

    suspend fun searchGroup(
        name: String,
        page: Int,
        pageSize: Int,
    ): ApiResult<GroupSearchApiResponse>

    fun getGroupDefaultImage(): Flow<ApiResult<RandomImageResponse>>

    fun getGroupDetail(groupId: Long): Flow<ApiResult<GroupDetailResponse>>

    fun getUserRank(
        groupId: Int,
        gameId: Int,
    ): Flow<ApiResult<UserRankApiResponse>>

    fun checkGroupJoinAccessCode(
        groupId: String,
        accessCode: String,
    ): Flow<ApiResult<CheckGroupJoinByAccessCodeResponse>>
}
