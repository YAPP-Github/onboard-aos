package com.yapp.bol.presentation.view.group.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.yapp.bol.domain.usecase.group.SearchGroupByKeywordUseCase
import com.yapp.bol.presentation.data.GroupSearchUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class GroupSearchViewModel @Inject constructor(
    private val searchGroupUseCase: SearchGroupByKeywordUseCase,
) : ViewModel() {

    fun searchGroup(keyword: String): Flow<PagingData<GroupSearchUiModel>> {
        val groups = searchGroupUseCase(keyword = keyword).cachedIn(viewModelScope)

        return groups
            .map { pagingData ->
                // 데이터가 존재하는 경우 아래와 같이 PagingData 생성
                pagingData.map {
                    GroupSearchUiModel.GroupList(it)
                }.insertSeparators { before, after ->
                    // 데이터가 존재하지 않는 경우 (before, after 데이터가 없는 경우) DataNotFound를 seperator로 생성
                    if (before == null && after == null)
                        return@insertSeparators GroupSearchUiModel.DataNotFound(keyword)
                    else
                        return@insertSeparators null
                }
            }
    }
}
