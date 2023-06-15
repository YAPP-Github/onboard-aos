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

    private val dynamicPlayers = arrayListOf<MemberItem>()

    init {
        getMembers()
    }

    fun updateToolBarTitle(title: String) {
        _toolBarTitle.value = title
    }

    private fun getMembers() {
        _members.value = testMembers
    }

    fun addSelectMembers(memberItem: MemberItem) {
        dynamicPlayers.add(memberItem)
        _players.value = createNewMembers()
    }

    fun removeSelectMembers(memberItem: MemberItem) {
        dynamicPlayers.remove(memberItem)
        _players.value = createNewMembers()
    }

    fun clearMembersChecked(position: Int) {
        val newMembers = members.value?.map { item ->
            if (item.id == position) item.copy(isChecked = false)
            else item
        }
        _members.value = newMembers ?: listOf()
    }

    private fun createNewMembers(): List<MemberItem> {
        return List(dynamicPlayers.size) {
            dynamicPlayers[it]
        }
    }

    companion object {
        val testMembers = List(10) {
            MemberItem(it, "$it. Test", 1)
        }
    }
}
