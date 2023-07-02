package com.yapp.bol.presentation.view.match.member_select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.mapper.Mapper.toPresentation
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MemberSelectViewModel @Inject constructor(
    private val matchUseCase: MatchUseCase
) : ViewModel() {

    private val _members = MutableLiveData(listOf<MemberInfo>())
    val members: LiveData<List<MemberInfo>> = _members

    private val _players = MutableLiveData(listOf<MemberInfo>())
    val players: LiveData<List<MemberInfo>> = _players

    private val _isCompleteButtonEnabled = MutableLiveData(false)
    val isCompleteButtonEnabled: LiveData<Boolean> = _isCompleteButtonEnabled

    private val _isNickNameValidate = MutableLiveData(false)
    val isNickNameValidate: LiveData<Boolean> = _isNickNameValidate

    val dynamicPlayers = arrayListOf<MemberInfo>()

    private var allMembers: List<MemberInfo> = listOf()
    private var loadingState = false
    private var groupId = 0
    private var cursor: String? = null
    private var hasNext = true

    init {
        getMembers()
    }

    fun getMembers(nickname: String? = null) {
        if (hasNext.not() || loadingState) return
        loadingState = true
        viewModelScope.launch {
            val memberList = withContext(Dispatchers.IO) { getMemberList(nickname) }
            allMembers = setMemberIsChecked(allMembers + memberList)
            updateMembers()
        }
    }

    fun addGuestMember(nickname: String) {
        viewModelScope.launch {
            postGuestMember(nickname)
        }
    }

    fun getValidateNickName(groupId: Int, nickname: String) {
        viewModelScope.launch {
            matchUseCase.getValidateNickName(groupId, nickname).collect {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _isNickNameValidate.value = data },
                    error = { throwable -> throw throwable }
                )
            }
        }
    }

    private suspend fun getMemberList(nickname: String? = null): List<MemberInfo> {
        var memberList = listOf<MemberInfo>()
        matchUseCase.getMemberList(groupId, 20, cursor, nickname).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { data ->
                    memberList = data.members.map { member -> member.toPresentation() }
                    cursor = data.cursor
                    hasNext = data.hasNext
                },
                error = { throwable -> throw throwable }
            )
        }
        return memberList
    }

    private suspend fun postGuestMember(nickname: String) {
        matchUseCase.postGuestMember(groupId, nickname)
        clearNextPage()
        getMembers()
    }

    fun updateMemberIsChecked(position: Int, isChecked: Boolean) {
        allMembers[position].isChecked = isChecked
    }

    fun checkedSelectMembers(memberInfo: MemberInfo) {
        if (players.value?.contains(memberInfo) ?: return) dynamicPlayers.remove(memberInfo)
        else dynamicPlayers.add(memberInfo)
        _players.value = dynamicPlayers.toList()
        checkedCompleteButtonEnabled()
    }

    fun clearMembers(position: Int) {
        allMembers = allMembers.map { if (it.id == position) it.copy(isChecked = false) else it }
        updateMembers()
    }

    fun clearNextPage() {
        allMembers = listOf()
        cursor = null
        hasNext = true
    }

    private fun updateMembers() {
        loadingState = false
        _members.value = allMembers.toList()
    }

    fun updateGroupId(id: Int) {
        groupId = id
    }

    private fun setMemberIsChecked(memberList: List<MemberInfo>): List<MemberInfo> {
        return memberList.map { memberInfo ->
            val isChecked = players.value?.any { it.id == memberInfo.id } ?: false
            if (isChecked) memberInfo.copy(isChecked = true)
            else memberInfo
        }
    }

    private fun checkedCompleteButtonEnabled() {
        _isCompleteButtonEnabled.value = (players.value?.size ?: 0) >= MIN_PLAYER_COUNT
    }

    companion object {
        const val MIN_PLAYER_COUNT = 2
    }
}
