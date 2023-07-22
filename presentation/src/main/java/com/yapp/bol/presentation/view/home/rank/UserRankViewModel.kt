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
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RANK_LOAD_FORCE_DELAY
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RV_1_TO_3_UI_RANK_THRESHOLD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
    private var userListFetchJob: Job? = null

    private val _groupListFlow = MutableStateFlow<List<DrawerGroupInfoUiModel>>(emptyList())
    val groupListFlow: StateFlow<List<DrawerGroupInfoUiModel>> = _groupListFlow

    private var selectedPosition: Int = 0

    private val _userUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val userUiState: StateFlow<HomeUiState> = _userUiState

    private val _groupUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val groupUiState: StateFlow<HomeUiState> = _groupUiState

    fun setGameItemSelected(newPosition: Int) {
        val gameUiList: MutableList<HomeGameItemUiModel> = _gameListFlow.value.toMutableList()
        val beforePosition = selectedPosition

        if (beforePosition == newPosition) { return }

        val beforeItem = gameUiList.getOrNull(beforePosition) as? HomeGameItemUiModel.GameItem
        beforeItem?.let {
            gameUiList[beforePosition] = HomeGameItemUiModel.GameItem(
                GameItemWithSelected(it.item.gameItem, false)
            )
        }

        val newItem = gameUiList.getOrNull(newPosition) as? HomeGameItemUiModel.GameItem
        newItem?.let {
            gameUiList[newPosition] = HomeGameItemUiModel.GameItem(
                GameItemWithSelected(it.item.gameItem, true)
            )
        }

        selectedPosition = newPosition
        _gameListFlow.value = gameUiList
    }

    fun fetchGameList(groupId: Long) {
        _groupUiState.value = HomeUiState.Loading

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
                        _groupUiState.value = HomeUiState.Success
                    },
                    error = { throwable -> _groupUiState.value = HomeUiState.Error(throwable) },
                )
            }
        }
    }

    fun fetchUserList(groupId: Long, gameId: Long) {
        val gameIdNullable: Long? = gameId
        fetchUserList(groupId, gameIdNullable)
    }

    private fun fetchUserList(groupId: Long, gameId: Long?) {
        _userUiState.value = HomeUiState.Loading
        userListFetchJob?.cancel()
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
                _userUiState.value = HomeUiState.Error(IllegalArgumentException("game not found"))
                0
            }
        }

        userListFetchJob = viewModelScope.launch {
            delay(USER_RANK_LOAD_FORCE_DELAY)

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
                        _userUiState.value = HomeUiState.Success
                    },
                    error = { throwable ->
                        _userUiState.value = HomeUiState.Error(IllegalArgumentException(throwable))
                    },
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
                    error = { throwable -> _groupUiState.value = HomeUiState.Error(throwable) }
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
                    error = { throwable -> _groupUiState.value = HomeUiState.Error(throwable) }
                )
            }
        }
    }
}
