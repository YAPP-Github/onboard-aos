package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.mapper.MapperToDomain.toDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource,
) : GroupRepository {

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
}
