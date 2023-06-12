package com.yapp.bol.presentation.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navercorp.nid.oauth.NidOAuthPreferencesManager.accessToken
import com.yapp.bol.domain.model.LoginItem
import com.yapp.bol.domain.model.OAuthApiItem
import com.yapp.bol.domain.usecase.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _loginResult = MutableStateFlow<LoginItem?>(null)
    val loginResult = _loginResult.asStateFlow()

    fun login(type: String, token: String) {
        viewModelScope.launch {
            _loginResult.emit(loginUseCase.execute(type, token))
        }
    }
}
