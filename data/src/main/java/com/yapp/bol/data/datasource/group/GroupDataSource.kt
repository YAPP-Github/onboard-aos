package com.yapp.bol.data.datasource.group

import com.yapp.bol.data.model.group.response.GroupSearchApiResponse
import com.yapp.bol.domain.model.ApiResult

interface GroupDataSource {

    suspend fun searchGroup(
        name: String,
        page: Int,
        pageSize: Int,
    ): ApiResult<GroupSearchApiResponse>
}
