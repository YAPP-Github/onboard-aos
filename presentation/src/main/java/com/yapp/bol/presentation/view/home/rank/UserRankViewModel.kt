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
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RANK_LOAD_FORCE_DELAY
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RV_1_TO_3_UI_RANK_THRESHOLD
import com.yapp.bol.presentation.view.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserRankViewModel @Inject constructor(
    private val getUserRankGameListUseCase: GetUserRankGameListUseCase,
    private val getUserRankUseCase: GetUserRankUseCase,
    private val getJoinedGroupUseCase: GetJoinedGroupUseCase,
    private val getGroupDetailUseCase: GetGroupDetailUseCase
) : ViewModel() {

    private var userListFetchJob: Job? = null

    private var selectedPosition: Int = RV_SELECTED_POSITION_RESET

    private val _userUiState = MutableStateFlow<HomeUiState<List<UserRankUiModel>>>(HomeUiState.Loading)
    val userUiState: StateFlow<HomeUiState<List<UserRankUiModel>>> = _userUiState

    private val _uiState = MutableStateFlow<HomeUiState<GameAndGroup>>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState<GameAndGroup>> = _uiState


    fun setGameItemSelected(newPosition: Int) {
        val gameUiList: MutableList<HomeGameItemUiModel> = uiState.value._data?.game?.toMutableList() ?: return

        val beforePosition = selectedPosition

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
        _uiState.value = HomeUiState.Success(GameAndGroup(gameUiList, _uiState.value._data!!.group))
    }

    fun fetchGameAndGroup(groupId: Long) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            val gameItemFlow = getUserRankGameListUseCase(groupId = groupId.toInt())
            val currentGroupFlow = getGroupDetailUseCase(groupId)
            val joinedGroupFlow = getJoinedGroupUseCase()
            val game: MutableList<HomeGameItemUiModel> = mutableListOf()
            val group: MutableList<DrawerGroupInfoUiModel> = mutableListOf()
            var gameIndex = -1
            var gameId = -1L

            gameItemFlow
                .combine(currentGroupFlow) { gameItem, currentGroup ->
                    checkedApiResult(
                        apiResult = gameItem,
                        success = { data ->
                            data.map {
                                game.add(HomeGameItemUiModel.GameItem(GameItemWithSelected(it, false)))
                            }
                            game.add(0, HomeGameItemUiModel.Padding)
                            game.add(HomeGameItemUiModel.Padding)

                            gameIndex = data.size / 2
                            gameId = data[data.size / 2].id
                        },
                        error = { throwable -> throw throwable }
                    )

                    checkedApiResult(
                        apiResult = currentGroup,
                        success = { data ->
                            group.add(DrawerGroupInfoUiModel.CurrentGroupInfo(data))
                        },
                        error = { throwable -> throw throwable }
                    )
                }
                .combine(joinedGroupFlow) { _, joinedGroup ->
                    checkedApiResult(
                        apiResult = joinedGroup,
                        success = { data ->
                            data.map { joinedGroupItem ->
                                if (joinedGroupItem.id != groupId) {
                                    group.add(DrawerGroupInfoUiModel.OtherGroupInfo(joinedGroupItem))
                                }
                            }
                        },
                        error = { throwable -> throw throwable }
                    )
                }
                .catch { _uiState.value = HomeUiState.Error(it) }
                .collectLatest {
                    _uiState.value = HomeUiState.Success(GameAndGroup(game, group))
                    fetchUserList(groupId, gameId)
                    setGameItemSelected(gameIndex)
                }
        }
    }

    fun getGameItemSelectedPosition(): Int = selectedPosition

    fun fetchUserList2(groupId: Long, gameId: Long) {
        val gameIdNullable: Long? = gameId
        fetchUserList(groupId, gameId)
    }

    private fun fetchUserList(groupId: Long, gameId: Long) {
        _userUiState.value = HomeUiState.Loading
        userListFetchJob?.cancel()

        userListFetchJob = viewModelScope.launch {
            delay(USER_RANK_LOAD_FORCE_DELAY)

            getUserRankUseCase(groupId.toInt(), gameId.toInt()).collectLatest {
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
                                userAfter4.add(UserRankUiModel.UserRankAfter4(UserRankItem(5L, 5, gameId.toString(), 0.34213, 1)))
                            }
                        }

                        val resultList = mutableListOf<UserRankUiModel>(UserRankUiModel.UserRank1to3(user1to3))
                        resultList.addAll(userAfter4)

                        _userUiState.value = HomeUiState.Success(resultList)
                    },
                    error = { throwable ->
                        _userUiState.value = HomeUiState.Error(IllegalArgumentException(throwable))
                    },
                )
            }
        }
    }

    companion object {
        const val RV_SELECTED_POSITION_RESET = -1
    }
}

data class GameAndGroup(
    val game: List<HomeGameItemUiModel>,
    val group: List<DrawerGroupInfoUiModel>,
)
