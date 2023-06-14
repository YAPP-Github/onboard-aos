package com.yapp.bol.domain.usecase.group

import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.repository.GroupRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGroupByKeywordUseCase @Inject constructor(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(
        name: String,
        page: Int,
        pageSize: Int,
    ): Flow<ApiResult<List<GroupSearchItem>>> =
        groupRepository.groupSearch(
            name = name,
            page = page,
            pageSize = pageSize,
        )
}
