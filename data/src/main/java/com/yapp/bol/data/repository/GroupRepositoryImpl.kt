package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.mapper.GroupMapper.mapperToCheckGroupJoinByAccessCodeItem
import com.yapp.bol.data.mapper.GroupMapper.newGroupToDomain
import com.yapp.bol.data.mapper.GroupMapper.toDetailItem
import com.yapp.bol.data.mapper.GroupMapper.toDomain
import com.yapp.bol.data.mapper.GroupMapper.toImageDomain
import com.yapp.bol.data.mapper.GroupMapper.toUserRankItem
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.CheckGroupJoinByAccessCodeItem
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.model.NewGroupItem
import com.yapp.bol.domain.model.UserRankListItem
import com.yapp.bol.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource,
) : GroupRepository {

    override fun postCreateGroup(
        name: String,
        description: String,
        organization: String,
        imageUrl: String,
        nickname: String
    ): Flow<ApiResult<NewGroupItem>> {
        return groupDataSource.postCreateGroup(name, description, organization, imageUrl, nickname).map {
            it.newGroupToDomain()
        }
    }

    override fun getUserRank(
        groupId: Int,
        gameId: Int
    ): Flow<ApiResult<UserRankListItem>> =
        groupDataSource.getUserRank(
            groupId = groupId,
            gameId = gameId
        ).map {
            it.toUserRankItem()
        }

    override fun getGroupDefaultImage(): Flow<ApiResult<String>> {
        return groupDataSource.getGroupDefaultImage().map { it.toImageDomain() }
    }

    override suspend fun searchGroup(
        name: String,
        page: Int,
        pageSize: Int
    ): ApiResult<GroupSearchItem> {
        return groupDataSource.searchGroup(
            name = name,
            page = page,
            pageSize = pageSize,
        ).toDomain()
    }

    override fun getGroupDetail(groupId: Long): Flow<ApiResult<GroupDetailItem>> =
        groupDataSource.getGroupDetail(groupId).map { it.toDetailItem() }

    override fun checkGroupJoinAccessCode(
        groupId: String,
        accessCode: String,
    ): Flow<ApiResult<CheckGroupJoinByAccessCodeItem>> {
        return groupDataSource.checkGroupJoinAccessCode(groupId, accessCode).map {
            it.mapperToCheckGroupJoinByAccessCodeItem()
        }
    }
}
