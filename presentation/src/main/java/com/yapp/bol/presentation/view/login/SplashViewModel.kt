package com.yapp.bol.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.user.group.MyGroupItem
import com.yapp.bol.domain.usecase.auth.GetAccessTokenUseCase
import com.yapp.bol.domain.usecase.login.LoginUseCase
import com.yapp.bol.domain.usecase.user.GetMyGroupListUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getMyGroupListUseCase: GetMyGroupListUseCase,
) : ViewModel() {

    private val  _animationState = MutableLiveData(false)
    val animationState: LiveData<Boolean> = _animationState

    private val _myGroupList = MutableLiveData<List<MyGroupItem>?>(null)
    val myGroupList: LiveData<List<MyGroupItem>?> = _myGroupList

    init {
        viewModelScope.launch {
            delay(2000)
            _animationState.value = true
        }
    }

    fun getMyGroupList() {
        viewModelScope.launch {
            getMyGroupListUseCase.execute().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _myGroupList.value = data },
                    error = { _myGroupList.value = listOf() }
                )
            }
        }
    }
}
