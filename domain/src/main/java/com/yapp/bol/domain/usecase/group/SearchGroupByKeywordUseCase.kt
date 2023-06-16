package com.yapp.bol.domain.usecase.group

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.paging.GroupListPagingSource
import com.yapp.bol.domain.repository.GroupRepository
import com.yapp.bol.domain.utils.GroupPagingConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchGroupByKeywordUseCase @Inject constructor(
    private val groupRepository: GroupRepository,
) {
    operator fun invoke(keyword: String): Flow<PagingData<GroupSearchItem>> {
        val groupPager = Pager(
            config = PagingConfig(
                pageSize = GroupPagingConfig.NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 3
            ),
            pagingSourceFactory = { GroupListPagingSource(groupRepository = groupRepository, keyword = keyword) }
        ).flow

        return groupPager.map { pagingData ->
            pagingData.map { groupSearchItem ->
                groupSearchItem
            }
        }
    }
}
