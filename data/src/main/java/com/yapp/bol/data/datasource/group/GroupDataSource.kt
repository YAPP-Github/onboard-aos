package com.yapp.bol.data.datasource.group

import com.yapp.bol.data.model.group.GroupSearchApiResponse
import com.yapp.bol.domain.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GroupDataSource {

    fun groupSearch(
        name: String,
        page: Int,
        pageSize: Int,
    ): Flow<ApiResult<GroupSearchApiResponse>>
}
