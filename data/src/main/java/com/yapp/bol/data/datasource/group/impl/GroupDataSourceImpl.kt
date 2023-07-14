package com.yapp.bol.data.datasource.group.impl

import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
import com.yapp.bol.data.remote.GroupApi
import com.yapp.bol.domain.handle.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(
    private val groupApi: GroupApi,
) : BaseRepository(), GroupDataSource {

    override suspend fun searchGroup(name: String, page: Int, pageSize: Int):
        ApiResult<GroupSearchApiResponse> {
        return safeApiCall {
            groupApi.getGroupSearchResult(
                name = name,
                page = page.toString(),
                pageSize = pageSize.toString(),
            )
        }
    }
}
