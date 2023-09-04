package com.yapp.bol.presentation.view.group.join

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GetGroupJoinedItem
import com.yapp.bol.domain.usecase.group.CheckGroupJoinByAccessCodeUseCase
import com.yapp.bol.domain.usecase.group.GetGroupJoinedUseCase
import com.yapp.bol.domain.usecase.group.JoinGroupUseCase
import com.yapp.bol.domain.usecase.login.MatchUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import com.yapp.bol.presentation.view.group.join.type.GroupResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupJoinViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val joinGroupUseCase: JoinGroupUseCase,
    private val checkGroupAccessCodeUseCase: CheckGroupJoinByAccessCodeUseCase,
    private val matchUseCase: MatchUseCase,
    private val getGroupJoinedUseCase: GetGroupJoinedUseCase,
) : ViewModel() {

    val groupItem = MutableStateFlow<GetGroupJoinedItem?>(null)

    val groupId = savedStateHandle.get<Int>("groupId") ?: 0

    private val _groupResult = MutableSharedFlow<GroupResultType>()
    val groupResult = _groupResult.asSharedFlow()

    val nickName get() = groupItem.value?.nickname.orEmpty()

    init {
        viewModelScope.launch {
            groupItem.emit(getGroupJoinedUseCase(groupId))
        }
    }

    fun isAlreadyJoinGroup(): Boolean = groupItem.value?.hasJoinedGroup == true

    fun joinGroup(accessCode: String, nickName: String) {
        viewModelScope.launch {
            validateNickName(nickName) { isAvailable ->
                if (isAvailable) {
                    launch {
                        implementJoinGroup(accessCode, nickName)
                    }
                } else {
                    setGroupResultType(GroupResultType.ValidationNickname())
                }
            }
        }
    }

    private suspend fun implementJoinGroup(accessCode: String, nickName: String) {
        joinGroupUseCase(groupId.toString(), accessCode, nickName).collectLatest {
            setGroupResultType(GroupResultType.LOADING())

            checkedApiResult(
                apiResult = it,
                success = {
                    setGroupResultType(GroupResultType.SUCCESS)
                },
                error = {
                    setGroupResultType(GroupResultType.UnknownError(it.message))
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

    private fun setGroupResultType(groupResultType: GroupResultType) {
        viewModelScope.launch {
            _groupResult.emit(groupResultType)
        }
    }
}
