package com.yapp.bol.presentation.view.match.member_select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberSelectViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val _members = MutableLiveData(listOf<MemberItem>())
    val members: LiveData<List<MemberItem>> = _members

    private val _players = MutableLiveData(listOf<MemberItem>())
    val players: LiveData<List<MemberItem>> = _players

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: LiveData<Boolean> = _isCompleteButtonEnabled

    private val _isNickNameValidate = MutableLiveData(false)
    val isNickNameValidate: LiveData<Boolean> = _isNickNameValidate

    val dynamicPlayers = arrayListOf<MemberItem>()

    private var allMembers = List(20) {
        MemberItem(it, "$it. Test", 1)
    }

    init {
        viewModelScope.launch {
            getMembers()
        }
    }

    fun getValidateNickName(groupId: Int, nickname: String) {
        viewModelScope.launch {
            matchUseCase.getValidateNickName(groupId, nickname).collect {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _isNickNameValidate.value = data },
                )
            }
        }
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
        const val MIN_PLAYER_COUNT = 2
    }
}
