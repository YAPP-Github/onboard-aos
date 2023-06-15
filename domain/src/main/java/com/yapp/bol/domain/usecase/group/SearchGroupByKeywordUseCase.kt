package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.repository.GroupRepository
import javax.inject.Inject

class SearchGroupByKeywordUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend fun invoke(
        name: String,
        page: Int,
        pageSize: Int,
    ): ApiResult<List<GroupSearchItem>> =
        groupRepository.searchGroup(
            name = name,
            page = page,
            pageSize = pageSize,
        )
}
