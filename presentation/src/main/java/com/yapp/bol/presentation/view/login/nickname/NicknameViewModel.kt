package com.yapp.bol.presentation.view.login.nickname

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    fun putUserName(nickname: String) {
        viewModelScope.launch {
            loginUseCase.putUserName(nickname)
        }
    }
}
