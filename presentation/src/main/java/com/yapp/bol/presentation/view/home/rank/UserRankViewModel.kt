package com.yapp.bol.presentation.view.home.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.group.GetGroupDetailUseCase
import com.yapp.bol.domain.usecase.group.GetJoinedGroupUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankGameListUseCase
import com.yapp.bol.domain.usecase.rank.GetUserRankUseCase
import com.yapp.bol.presentation.mapper.HomeMapper.toHomeGameItemUiModelList
import com.yapp.bol.presentation.mapper.HomeMapper.toOtherGroupInfoUiModel
import com.yapp.bol.presentation.mapper.HomeMapper.toUserRankUiModel
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel
import com.yapp.bol.presentation.model.GameItemWithSelected
import com.yapp.bol.presentation.model.HomeGameItemUiModel
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.utils.config.HomeConfig.USER_RANK_LOAD_FORCE_DELAY
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

    private val _gameAndGroupUiState = MutableStateFlow<HomeUiState<GameAndGroup>>(HomeUiState.Loading)
    val gameAndGroupUiState: StateFlow<HomeUiState<GameAndGroup>> = _gameAndGroupUiState

    var groupId: Long = GAME_USER_ID_TO_BE_SET
    var gameId: Long = GAME_USER_ID_TO_BE_SET

    fun setGameItemSelected(newPosition: Int) {
        val gameUiList: MutableList<HomeGameItemUiModel> =
            gameAndGroupUiState.value._data?.game?.toMutableList() ?: return
        val groupUiList: List<DrawerGroupInfoUiModel> = gameAndGroupUiState.value._data?.group ?: return

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
        _gameAndGroupUiState.value = HomeUiState.Success(GameAndGroup(gameUiList, groupUiList))
    }

    fun fetchAll(initGroupId: Long? = null, initGameId: Long? = null) {
        initGroupId?.let {
            groupId = it
            initGameId?.let { id -> gameId = id }
            fetchAllFromServer(it, initGameId)
        } ?: kotlin.run {
            fetchAllFromServer(groupId)
        }
    }

    private fun fetchAllFromServer(groupId: Long, initGameId: Long? = null) {
        viewModelScope.launch {
            _gameAndGroupUiState.value = HomeUiState.Loading

            val startPadding = 1

            val gameItemFlow = getUserRankGameListUseCase(groupId = groupId.toInt())
            val currentGroupFlow = getGroupDetailUseCase(groupId)
            val joinedGroupFlow = getJoinedGroupUseCase()
            val game: MutableList<HomeGameItemUiModel> = mutableListOf()
            val group: MutableList<DrawerGroupInfoUiModel> = mutableListOf()
            var gameIndex = -1

            fun <T> List<T>.middleIndex() = this.size / 2

            gameItemFlow
                .combine(currentGroupFlow) { gameItem, currentGroup ->
                    checkedApiResult(
                        apiResult = gameItem,
                        success = { data ->
                            game.addAll(data.toHomeGameItemUiModelList())

                            initGameId?.let {
                                data.mapIndexed { index, gameItem ->
                                    if (gameItem.id == initGameId) { gameIndex = index + startPadding }
                                }
                                gameId = it
                            } ?: kotlin.run {
                                gameIndex = data.middleIndex() + startPadding
                                gameId = data[data.middleIndex()].id
                            }
                        },
                        error = { throwable -> throw Exception(throwable.code) }
                    )

                    checkedApiResult(
                        apiResult = currentGroup,
                        success = { data -> group.add(DrawerGroupInfoUiModel.CurrentGroupInfo(data)) },
                        error = { throwable -> throw Exception(throwable.code) }
                    )
                }
                .combine(joinedGroupFlow) { _, joinedGroup ->
                    checkedApiResult(
                        apiResult = joinedGroup,
                        success = { data -> group.addAll(data.toOtherGroupInfoUiModel(currentGroupId = groupId)) },
                        error = { throwable -> throw Exception(throwable.code) }
                    )
                }
                .catch { _gameAndGroupUiState.value = HomeUiState.Error(it) }
                .collectLatest {
                    _gameAndGroupUiState.value = HomeUiState.Success(GameAndGroup(game, group))
                    fetchUserListFromServer(groupId, gameId)
                    setGameItemSelected(gameIndex)
                }
        }
    }

    fun getGameItemSelectedPosition(): Int = selectedPosition

    fun fetchUserList(initGameId: Long? = null) {
        initGameId?.let {
            gameId = it
        }
        fetchUserListFromServer(groupId, gameId)
    }

    private fun fetchUserListFromServer(groupId: Long, gameId: Long) {
        _userUiState.value = HomeUiState.Loading
        userListFetchJob?.cancel()

        userListFetchJob = viewModelScope.launch {
            delay(USER_RANK_LOAD_FORCE_DELAY)

            getUserRankUseCase(groupId.toInt(), gameId.toInt()).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _userUiState.value = HomeUiState.Success(data.toUserRankUiModel()) },
                    error = { throwable ->
                        _userUiState.value = HomeUiState.Error(IllegalArgumentException(Exception(throwable.code)))
                    },
                )
            }
        }
    }

    companion object {
        const val RV_SELECTED_POSITION_RESET = -1
        private const val GAME_USER_ID_TO_BE_SET: Long = -1L
    }
}

data class GameAndGroup(
    val game: List<HomeGameItemUiModel>,
    val group: List<DrawerGroupInfoUiModel>,
)
