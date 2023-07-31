package com.yapp.bol.presentation.view.login.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.domain.usecase.group.GetJoinedGroupUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getJoinedGroupUseCase: GetJoinedGroupUseCase,
) : ViewModel() {

    private val _animationState = MutableLiveData(false)
    val animationState: LiveData<Boolean> = _animationState

    private val _myGroupList = MutableLiveData<List<JoinedGroupItem>?>(null)
    val myGroupList: LiveData<List<JoinedGroupItem>?> = _myGroupList

    private val _networkState = MutableLiveData(false)
    val networkState: LiveData<Boolean> = _networkState

    init {
        viewModelScope.launch {
            delay(2000)
            _animationState.value = true
        }
    }

    fun getMyGroupList() {
        viewModelScope.launch {
            getJoinedGroupUseCase().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _myGroupList.value = data },
                    error = { item ->
                        when (item.code) {
                            "Auth004" -> _myGroupList.value = listOf()
                            else -> _networkState.value = true
                        }
                    }
                )
            }
        }
    }
}
