package com.yapp.bol.presentation.view.group.join

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.GroupItem
import com.yapp.bol.domain.usecase.group.JoinGroupUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupJoinViewModel @Inject constructor(
    private val joinGroupUseCase: JoinGroupUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _loading = MutableSharedFlow<Pair<Boolean, String>>()
    val loading = _loading.asSharedFlow()

    val groupItem = savedStateHandle.getStateFlow<GroupItem?>("groupItem", null)

    private val _successJoinGroup = MutableSharedFlow<Pair<Boolean, String?>>()
    val successJoinGroup = _successJoinGroup.asSharedFlow()

    fun joinGroup(accessCode: String, nickName: String) {
        viewModelScope.launch {
            _loading.emit(true to "모임에 들어가는 중")
            joinGroupUseCase(groupItem.value?.id.toString(), "8D20YS", nickName).collectLatest {
                _loading.emit(false to "")
                checkedApiResult(
                    apiResult = it,
                    success = {
                        Log.d("Debug", "joinGroup: success")
                        viewModelScope.launch { // 이게 최선인가 ?
                            _successJoinGroup.emit(true to null)
                        }
                    },
                    error = {
                        Log.d("Debug", "joinGroup: fail")
                        viewModelScope.launch {
                            _successJoinGroup.emit(false to it.message)
                        }
                    },
                )
            }
        }
    }
}
