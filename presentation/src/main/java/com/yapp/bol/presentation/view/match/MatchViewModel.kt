package com.yapp.bol.presentation.view.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import javax.inject.Inject

class MatchViewModel @Inject constructor() : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle = _toolBarTitle

    private val _members = MutableLiveData<List<MemberItem>>(listOf())
    val members = _members

    private val _players = MutableLiveData(listOf<MemberItem>())
    val players = _players

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled = _isCompleteButtonEnabled

    private val dynamicPlayers = arrayListOf<MemberItem>()

    init {
        getMembers()
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
