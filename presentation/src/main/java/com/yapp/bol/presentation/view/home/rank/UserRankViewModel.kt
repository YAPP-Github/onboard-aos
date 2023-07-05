package com.yapp.bol.presentation.view.home.rank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GameItem
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.usecase.group.GetGroupDetailUseCase
import com.yapp.bol.domain.usecase.group.GetJoinedGroupUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankGameListUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankUseCase
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRankViewModel @Inject constructor(
    private val getUserRankGameListUseCase: GetUserRankGameListUseCase,
    private val getUserRankUseCase: GetUserRankUseCase,
    private val getJoinedGroupUseCase: GetJoinedGroupUseCase,
    private val getGroupDetailUseCase: GetGroupDetailUseCase
) : ViewModel() {

    private val _gameListFlow = MutableStateFlow<List<GameItem>>(emptyList())
    val gameListFlow: StateFlow<List<GameItem>> = _gameListFlow

    private val _userListFlow = MutableStateFlow<List<UserRankUiModel>>(emptyList())
    val userListFlow: StateFlow<List<UserRankUiModel>> = _userListFlow

    private val _groupListFlow = MutableStateFlow<List<DrawerGroupInfoUiModel>>(emptyList())
    val groupListFlow: StateFlow<List<DrawerGroupInfoUiModel>> = _groupListFlow

    init {
        fetchGameList()
        fetchUserList()
        fetchJoinedGroupList(1)
    }

    private fun fetchGameList() {
        viewModelScope.launch {
            getUserRankGameListUseCase(999).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _gameListFlow.value = data },
                    error = { throwable -> throw throwable },
                )
            }
        }
    }

    private fun fetchUserList() {
        _userListFlow.value = emptyList()

        viewModelScope.launch {
            getUserRankUseCase(90, 0).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data ->
                        val user1to3 = mutableListOf<UserRankItem>()
                        val userAfter4 = mutableListOf<UserRankUiModel>()

                        data.userRankItemList.map { item ->
                            if (item.rank <= 3) {
                                user1to3.add(item)
                            } else {
                                userAfter4.add(UserRankUiModel.UserRankAfter4(item))
                            }
                        }

                        val resultList = mutableListOf<UserRankUiModel>(UserRankUiModel.UserRank1to3(user1to3))
                        resultList.addAll(userAfter4)

                        _userListFlow.value = resultList
                    },
                    error = { throwable -> throw throwable },
                )
            }
        }
    }

    private fun fetchJoinedGroupList(groupId: Long) {
        viewModelScope.launch {
            val uiModelList = mutableListOf<DrawerGroupInfoUiModel>()

            getGroupDetailUseCase(groupId).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { groupDetailItem ->
                        uiModelList.add(DrawerGroupInfoUiModel.CurrentGroupInfo(groupDetailItem))
                    },
                    error = { throwable -> throw throwable }
                )
            }

            getJoinedGroupUseCase().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { joinedGroupItemList ->
                        joinedGroupItemList.map { joinedGroupItem ->
                            if (joinedGroupItem.id != groupId) {
                                uiModelList.add(DrawerGroupInfoUiModel.OtherGroupInfo(joinedGroupItem))
                            }
                        }
                        _groupListFlow.value = uiModelList
                    },
                    error = { throwable -> throw throwable }
                )
            }
        }
    }
}
