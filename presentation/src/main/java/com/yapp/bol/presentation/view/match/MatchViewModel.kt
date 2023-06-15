package com.yapp.bol.presentation.view.match

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.utils.Constant.GAME_RESULT_RECORD
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MatchViewModel @Inject constructor() : ViewModel() {

    private val _toolBarTitle = MutableLiveData(GAME_RESULT_RECORD)
    val toolBarTitle = _toolBarTitle

    private val _members = MutableLiveData<List<MemberItem>>(listOf())
    val members = _members

    private val _players = MutableStateFlow(listOf<MemberItem>())
    val players = _players.asStateFlow()

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

    fun checkedSelectMembers(memberItem: MemberItem) {
        if (players.value.contains(memberItem)) dynamicPlayers.remove(memberItem)
        else dynamicPlayers.add(memberItem)
        _players.value = dynamicPlayers.toList()
        checkedCompleteButtonEnabled()
    }

    fun updateMembersIsChecked(position: Int, value: Boolean) {
        allMembers[position].isChecked = value
    }

    fun clearMembers(position: Int? = null, search: String) {
        if (position == null) {
            updateSearchMembers(search)
        } else {
            val newMembers = allMembers.filter { it.name.contains(search) }.map { member ->
                if (member.id == position) member.copy(isChecked = false)
                else member
            }
            _members.value = newMembers
            allMembers[position].isChecked = false
        }
    }

    private fun updateSearchMembers(search: String) {
        _members.value = allMembers.filter { it.name.contains(search) }
    }

    private fun checkedCompleteButtonEnabled() {
        _isCompleteButtonEnabled.value = players.value.size > 1
    }

    companion object {
        private val allMembers = List(20) {
            MemberItem(it, "$it. Test", 1)
        }
    }
}
