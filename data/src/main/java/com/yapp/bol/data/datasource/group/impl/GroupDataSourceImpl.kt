package com.yapp.bol.data.datasource.group.impl

import com.yapp.bol.data.datasource.group.GroupDataSource
import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.data.remote.GroupApi
import com.yapp.bol.data.utils.BaseRepository
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(
    private val groupApi: GroupApi
) : BaseRepository(), GroupDataSource {

    override fun groupSearch(name: String, page: Int, pageSize: Int):
        Flow<ApiResult<GroupSearchApiResponse>> = flow {
        val result = safeApiCall {
            groupApi.getGroupSearchResult(
                name = name,
                page = page.toString(),
                pageSize = pageSize.toString()
            )
        }
        emit(result)
    }
}
