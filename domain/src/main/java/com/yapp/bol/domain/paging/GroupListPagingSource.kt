package com.yapp.bol.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yapp.bol.domain.model.ApiResult
import com.yapp.bol.domain.model.GroupSearchItem
import com.yapp.bol.domain.repository.GroupRepository
import javax.inject.Inject

class GroupListPagingSource @Inject constructor(
    private val groupRepository: GroupRepository,
) : PagingSource<Int, GroupSearchItem>() {
    override fun getRefreshKey(state: PagingState<Int, GroupSearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupSearchItem> {
        val page = params.key ?: 1
        return try {
            val response = groupRepository.searchGroup(
                name = "your_name",
                page = page,
                pageSize = params.loadSize
            )
            val success = response as ApiResult.Success
            LoadResult.Page(
                data = success.data,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (response.data.isNotEmpty()) page + 1 else null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}
