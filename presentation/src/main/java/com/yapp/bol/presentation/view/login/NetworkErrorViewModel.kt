package com.yapp.bol.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.domain.usecase.group.GetJoinedGroupUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkErrorViewModel @Inject constructor(
    private val getJoinedGroupUseCase: GetJoinedGroupUseCase,
) : ViewModel() {

    private val _myGroupList = MutableLiveData<List<JoinedGroupItem>?>(null)
    val myGroupList: LiveData<List<JoinedGroupItem>?> = _myGroupList

    fun getMyGroupList() {
        viewModelScope.launch {
            getJoinedGroupUseCase().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _myGroupList.value = data },
                )
            }
        }
    }
}
