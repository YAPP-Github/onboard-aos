package com.yapp.bol.presentation.view.home.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.UserRankItem
import com.yapp.bol.domain.usecase.group.GetGroupDetailUseCase
import com.yapp.bol.domain.usecase.group.GetJoinedGroupUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankGameListUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankUseCase
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel
import com.yapp.bol.presentation.model.GameItemWithSelected
import com.yapp.bol.presentation.model.HomeGameItemUiModel
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.utils.config.HomeConfig.GAME_RV_FIRST_POSITION
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RV_1_TO_3_UI_RANK_THRESHOLD
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

    private val _gameListFlow = MutableStateFlow<List<HomeGameItemUiModel>>(emptyList())
    val gameListFlow: StateFlow<List<HomeGameItemUiModel>> = _gameListFlow

    private val _userListFlow = MutableStateFlow<List<UserRankUiModel>>(emptyList())
    val userListFlow: StateFlow<List<UserRankUiModel>> = _userListFlow

    private val _groupListFlow = MutableStateFlow<List<DrawerGroupInfoUiModel>>(emptyList())
    val groupListFlow: StateFlow<List<DrawerGroupInfoUiModel>> = _groupListFlow

    private val _selectedPosition = MutableStateFlow<Int>(0)
    val selectedPosition: StateFlow<Int> = _selectedPosition

    init {
        // TODO : const 변경 필요
        fetchGameList(999)
        fetchJoinedGroupList(3)
    }

    fun setGameItemSelected(index: Int) {
        val gameUiList: MutableList<HomeGameItemUiModel> = _gameListFlow.value.toMutableList()

        selectedPosition.value.let {
            if (_gameListFlow.value.size > it && gameUiList[it] is HomeGameItemUiModel.GameItem) {
                val gameItem = (gameUiList[it] as HomeGameItemUiModel.GameItem).item.gameItem
                gameUiList[it] = HomeGameItemUiModel.GameItem(GameItemWithSelected(gameItem, false))
            }
        }

        _selectedPosition.value = index
        val gameItem = (gameUiList[index] as HomeGameItemUiModel.GameItem).item.gameItem
        gameUiList[index] = HomeGameItemUiModel.GameItem(GameItemWithSelected(gameItem, true))

        _gameListFlow.value = gameUiList
    }

    fun fetchGameList(groupId: Long) {
        viewModelScope.launch {
            getUserRankGameListUseCase(groupId.toInt()).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data ->
                        val gameUiList: MutableList<HomeGameItemUiModel> = data.map { gameItem ->
                            HomeGameItemUiModel.GameItem(GameItemWithSelected(gameItem, false))
                        }.toMutableList()

                        gameUiList.add(0, HomeGameItemUiModel.Padding)
                        gameUiList.add(HomeGameItemUiModel.Padding)

                        _gameListFlow.value = gameUiList
                        fetchUserList(groupId, null)
                    },
                    error = { throwable -> throw throwable },
                )
            }
        }
    }

    fun fetchUserList(groupId: Long, gameId: Long) {
        val gameIdNullable: Long? = gameId
        fetchUserList(groupId, gameIdNullable)
    }

    private fun fetchUserList(groupId: Long, gameId: Long?) {
        _userListFlow.value = emptyList()

        if (gameId == null) {
            setGameItemSelected(GAME_RV_FIRST_POSITION)
        }

        val gameIdNotNull: Long = gameId ?: kotlin.run {
            if (_gameListFlow.value.isNotEmpty()) {
                var firstItemId: Long = 0
                _gameListFlow.value.map {
                    if (it is HomeGameItemUiModel.GameItem) {
                        firstItemId = it.item.gameItem.id
                        return@map
                    }
                }
                firstItemId
            } else {
                throw IllegalArgumentException("game not found")
            }
        }

        viewModelScope.launch {
            getUserRankUseCase(groupId.toInt(), gameIdNotNull.toInt()).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data ->
                        val user1to3 = mutableListOf<UserRankItem>()
                        val userAfter4 = mutableListOf<UserRankUiModel>()

                        data.userRankItemList.map { item ->
                            if (item.rank <= USER_RV_1_TO_3_UI_RANK_THRESHOLD) {
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

    fun fetchJoinedGroupList(groupId: Long) {
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
