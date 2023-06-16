package com.yapp.bol.presentation.view.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle = _toolBarTitle

    private val _members = MutableLiveData<List<MemberItem>>(listOf())
    val members = _members

    private val _players = MutableLiveData(listOf<MemberItem>())
    val players = _players

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled = _isCompleteButtonEnabled

    private val _gameList = MutableLiveData(listOf<com.yapp.bol.domain.model.GameItem>())
    val gameList = _gameList

    private val dynamicPlayers = arrayListOf<MemberItem>()

    init {
        initialized()
    }

    private fun initialized() {
        viewModelScope.launch {
            getGameList()
            getMembers()
        }
    }

    private suspend fun getGameList() {
        matchUseCase.getGameList(9999).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data -> _gameList.value = data },
                error = { throwable -> throw throwable },
            )
        }
    }

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }

    private fun getMembers() {
        _members.value = allMembers
    }

    fun updateMemberIsChecked(position: Int, isChecked: Boolean) {
        allMembers[position].isChecked = isChecked
    }

    fun checkedSelectMembers(memberItem: MemberItem) {
        if (players.value?.contains(memberItem) ?: return) dynamicPlayers.remove(memberItem)
        else dynamicPlayers.add(memberItem)
        _players.value = dynamicPlayers.toList()
        checkedCompleteButtonEnabled()
    }

    fun clearMembers(position: Int, search: String) {
        allMembers = allMembers.map { if (it.id == position) it.copy(isChecked = false) else it }
        updateSearchMembers(search)
    }

    fun updateSearchMembers(search: String) {
        _members.value = allMembers.filter { it.name.contains(search) }
    }

    private fun checkedCompleteButtonEnabled() {
        _isCompleteButtonEnabled.value = (players.value?.size ?: 0) >= MIN_PLAYER_COUNT
    }

    companion object {
        private var allMembers = List(20) {
            MemberItem(it, "$it. Test", 1)
        }
        const val MIN_PLAYER_COUNT = 2
    }
}
