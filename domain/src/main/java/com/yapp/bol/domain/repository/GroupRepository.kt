package com.yapp.bol.domain.repository

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun groupSearch(
        name: String,
        page: Int,
        pageSize: Int,
    ): Flow<ApiResult<List<GroupSearchItem>>>
}
