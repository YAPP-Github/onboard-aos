package com.yapp.bol.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.usecase.auth.SaveAccessTokenUseCase
import com.yapp.bol.domain.usecase.auth.SaveRefreshTokenUseCase
import com.yapp.bol.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
) : ViewModel() {

    private val _loginResult = MutableStateFlow<LoginItem?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun login(type: String, token: String) {
        viewModelScope.launch {
            _loginResult.emit(loginUseCase.execute(type, token))
            _loginResult.value?.let {
                saveAccessToken(it.accessToken)
                saveRefreshToken(it.refreshToken)
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
