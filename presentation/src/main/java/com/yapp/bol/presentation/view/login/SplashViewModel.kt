package com.yapp.bol.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.auth.GetAccessTokenUseCase
import com.yapp.bol.domain.usecase.login.LoginUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
) : ViewModel() {

    private val _accessToken = MutableLiveData<String?>(null)
    val accessToken: LiveData<String?> = _accessToken

    init {
        viewModelScope.launch {
            getAccessTokenUseCase().collectLatest {
                _accessToken.value = it
            }
        }
    }
}
