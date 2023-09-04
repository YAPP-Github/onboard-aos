package com.yapp.bol.presentation.view.group.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GetGroupJoinedItem
import com.yapp.bol.domain.model.Role
import com.yapp.bol.domain.usecase.group.CheckGroupJoinByAccessCodeUseCase
import com.yapp.bol.domain.usecase.group.GetGroupJoinedUseCase
import com.yapp.bol.domain.usecase.group.JoinGroupUseCase
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.mapper.MatchMapper.toPresentation
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.view.group.join.type.GroupResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupJoinViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
    private val checkGroupAccessCodeUseCase: CheckGroupJoinByAccessCodeUseCase,
    private val matchUseCase: MatchUseCase,
    private val getGroupJoinedUseCase: GetGroupJoinedUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _successCheckGroupAccessCode = MutableSharedFlow<Pair<Boolean, String?>>()
    val successCheckGroupAccessCode = _successCheckGroupAccessCode.asSharedFlow()

    private val _loading = MutableSharedFlow<Pair<Boolean, String>>()
    val loading = _loading.asSharedFlow()

    val groupItem = MutableStateFlow<GetGroupJoinedItem?>(null)

    private val groupId = savedStateHandle.get<Int>("groupId") ?: 0

    private val _successJoinGroup = MutableSharedFlow<Pair<Boolean, String?>>()
    val successJoinGroup = _successJoinGroup.asSharedFlow()

    private val _guestList = MutableLiveData<List<MemberInfo>>(listOf())
    val guestList: LiveData<List<MemberInfo>> = _guestList

    private val _groupResult = MutableSharedFlow<GroupResultType>()
    val groupResult = _groupResult.asSharedFlow()

    var nickName = ""
    var accessCode = ""

    private var loadingState = false
    private var cursor: String? = null
    private var hasNext = true

    init {
        viewModelScope.launch {
            getGroupJoinedUseCase(groupId).run {
                groupItem.emit(this)

                nickName = this?.nickname.orEmpty()
            }
        }
    }

    fun isAlreadyJoinGroup(): Boolean {
        return groupItem.value?.hasJoinedGroup == true
    }

    fun joinGroup(accessCode: String, nickName: String, guestId: Int? = null) {
        viewModelScope.launch {
            _loading.emit(true to "모임에 들어가는 중")

            validateNickName(nickName) { isAvailable ->
                if (isAvailable || guestId != null) {
                    launch {
                        implementJoinGroup(accessCode, nickName, guestId)
                    }
                } else {
                    launch {
                        setGroupResultType(GroupResultType.ValidationNickname())
                    }
                }
            }
        }
    }

    private suspend fun implementJoinGroup(accessCode: String, nickName: String, guestId: Int?) {
        joinGroupUseCase(groupId.toString(), accessCode, nickName, guestId).collectLatest {
            setGroupResultType(GroupResultType.LOADING())
            checkedApiResult(
                apiResult = it,
                success = {
                    viewModelScope.launch {
                        setGroupResultType(GroupResultType.SUCCESS)
                    }
                },
                error = {
                    viewModelScope.launch {
                        setGroupResultType(GroupResultType.UnknownError(it.message))
                    }
                },
            )
        }
    }

    private suspend fun validateNickName(
        nickName: String,
        successValidateNickname: (Boolean) -> Unit,
    ) {
        matchUseCase.getValidateNickName(groupId, nickName).collectLatest {
            checkedApiResult(
                apiResult = it,
                success = { isAvailable ->
                    successValidateNickname.invoke(isAvailable)
                },
            )
        }
    }

    fun checkGroupJoinByAccessCode(accessCode: String) {
        viewModelScope.launch {
            checkGroupAccessCodeUseCase(groupId.toString(), accessCode).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = {
                        if (it.isNewMember) {
                            setGroupResultType(GroupResultType.SUCCESS)
                        } else {
                            setGroupResultType(GroupResultType.ValidationAccessCode())
                        }
                    },
                    error = {
                        viewModelScope.launch {
                            setGroupResultType(GroupResultType.UnknownError(it.message))
                        }
                    },
                )
            }
        }
    }

    fun getMembers(nickname: String? = null) {
        if (hasNext.not() || loadingState) return
        loadingState = true
        viewModelScope.launch {
            val memberList = withContext(Dispatchers.IO) { getMemberList(nickname) }
            val guest = memberList.filter { it.role == Role.GUEST.string } // Role 부분 파일 분리해도 좋을 듯 ?
            _guestList.value = guest
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
            )
        }
        return memberList
    }

    private fun setGroupResultType(groupResultType: GroupResultType) {
        viewModelScope.launch {
            _groupResult.emit(groupResultType)
        }
    }
}
