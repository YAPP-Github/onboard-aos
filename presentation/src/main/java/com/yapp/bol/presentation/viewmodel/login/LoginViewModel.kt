package com.yapp.bol.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.usecase.login.LoginUseCase
import com.yapp.bol.domain.utils.ErrorType
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), RemoteErrorEmitter {

    private val _loginResult = MutableStateFlow<LoginItem?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun login(type: String, token: String) {
        viewModelScope.launch {
            _loginResult.emit(loginUseCase.execute(this@LoginViewModel, type, token))
        }
    }

    override fun onError(msg: String) {
        // todo error handling
    }

    override fun onError(errorType: ErrorType) {
        // todo error handling
    }
}
