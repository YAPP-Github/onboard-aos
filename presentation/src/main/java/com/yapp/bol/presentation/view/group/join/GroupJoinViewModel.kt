package com.yapp.bol.presentation.view.group.join

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.domain.usecase.group.CheckGroupJoinByAccessCodeUseCase
import com.yapp.bol.domain.usecase.group.GetGroupDetailUseCase
import com.yapp.bol.domain.usecase.group.JoinGroupUseCase
import com.yapp.bol.domain.usecase.login.GetMyInfoUseCase
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupJoinViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
    private val checkGroupAccessCodeUseCase: CheckGroupJoinByAccessCodeUseCase,
    private val matchUseCase: MatchUseCase,
    private val getGroupItemUseCase: GetGroupDetailUseCase,
    private val GetMyInfoUseCase: GetMyInfoUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _successCheckGroupAccessCode = MutableSharedFlow<Pair<Boolean, String?>>()
    val successCheckGroupAccessCode = _successCheckGroupAccessCode.asSharedFlow()

    private val _loading = MutableSharedFlow<Pair<Boolean, String>>()
    val loading = _loading.asSharedFlow()

    val groupItem = MutableStateFlow<GroupDetailItem?>(null)

    private val groupId = savedStateHandle.get<Int>("groupId") ?: 0

    private val _successJoinGroup = MutableSharedFlow<Pair<Boolean, String?>>()
    val successJoinGroup = _successJoinGroup.asSharedFlow()

    var nickName = ""

    init {
        viewModelScope.launch {
            getGroupItemUseCase.invoke(groupId.toLong()).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { groupItem ->
                        viewModelScope.launch {
                            this@GroupJoinViewModel.groupItem.emit(groupItem)
                        }
                    },
                )
            }
        }
        viewModelScope.launch {
            GetMyInfoUseCase.invoke().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { user ->
                        viewModelScope.launch {
                            nickName = user.nickname
                        }
                    },
                )
            }
        }
    }

    fun joinGroup(accessCode: String, nickName: String) {
        viewModelScope.launch {
            _loading.emit(true to "모임에 들어가는 중")

            validateNickName(nickName) { isAvailable ->
                if (isAvailable) {
                    launch {
                        implementJoinGroup(accessCode, nickName)
                    }
                } else {
                    launch {
                        _successJoinGroup.emit(false to "이미 있는 이름입니다. 다른 이름을 설정해주세요.")
                    }
                }
            }
        }
    }

    private suspend fun implementJoinGroup(accessCode: String, nickName: String) {
        joinGroupUseCase(groupId.toString(), accessCode, nickName).collectLatest {
            _loading.emit(false to "")
            checkedApiResult(
                apiResult = it,
                success = {
                    viewModelScope.launch {
                        _successJoinGroup.emit(true to null)
                    }
                },
                error = {
                    viewModelScope.launch {
                        _successJoinGroup.emit(false to it.message)
                    }
                },
            )
        }
    }

    private suspend fun validateNickName(
        nickName: String,
        successValidateNickname: (Boolean) -> Unit,
    ) {
        matchUseCase.getValidateNickName(groupId.toInt(), nickName).collectLatest {
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
                        viewModelScope.launch {
                            if (it.isNewMember) {
                                _successCheckGroupAccessCode.emit(true to null)
                            } else {
                                _successCheckGroupAccessCode.emit(false to "참여 코드가 맞지 않습니다.")
                            }
                        }
                    },
                    error = {
                        viewModelScope.launch {
                            _successCheckGroupAccessCode.emit(false to it.message)
                        }
                    },
                )
            }
        }
    }
}
