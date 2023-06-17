package com.yapp.bol.presentation.view.group.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.yapp.bol.domain.usecase.group.SearchGroupByKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * 테스트용으로 만들어둔 파일입니다.
 */
@HiltViewModel
class GroupSearchViewModel @Inject constructor(
    private val searchGroupUseCase: SearchGroupByKeywordUseCase,
) : ViewModel() {

    fun searchGroup(keyword: String): Flow<PagingData<GroupSearchUiModel>> {
        val groups = searchGroupUseCase(keyword = keyword).cachedIn(viewModelScope)
        return groups.map { pagingData ->
            pagingData.map {
                Log.d("ccheck", it.toString())
                GroupSearchUiModel.GroupList(it)
            }
        }
    }
}
