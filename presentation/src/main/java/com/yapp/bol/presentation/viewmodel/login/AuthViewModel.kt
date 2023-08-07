package com.yapp.bol.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.usecase.auth.SaveAccessTokenUseCase
import com.yapp.bol.domain.usecase.auth.SaveRefreshTokenUseCase
import com.yapp.bol.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
) : ViewModel() {

    private val _loginResult = MutableSharedFlow<LoginItem?>()
    val loginResult = _loginResult.asSharedFlow()

    fun login(type: String, token: String) {
        viewModelScope.launch {
            loginUseCase.execute(type, token)?.let {
                saveAccessToken(it.accessToken)
                saveRefreshToken(it.refreshToken)
                _loginResult.emit(it)
            }
        }
    }

    private fun saveAccessToken(token: String) {
        viewModelScope.launch {
            saveAccessTokenUseCase(token)
        }
    }

    private fun saveRefreshToken(token: String) {
        viewModelScope.launch {
            saveRefreshTokenUseCase(token)
        }
    }
}
