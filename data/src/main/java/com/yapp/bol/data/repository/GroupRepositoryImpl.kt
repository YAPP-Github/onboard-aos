package com.yapp.bol.data.repository

import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.mapper.MapperToDomain.toDomain
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupDataSource: GroupDataSource,
) : GroupRepository {

    override fun groupSearch(
        name: String,
        page: Int,
        pageSize: Int
    ): Flow<ApiResult<List<GroupSearchItem>>> {
        return groupDataSource.groupSearch(
            name = name,
            page = page,
            pageSize = pageSize,
        ).map { it.toDomain() }
    }
}
