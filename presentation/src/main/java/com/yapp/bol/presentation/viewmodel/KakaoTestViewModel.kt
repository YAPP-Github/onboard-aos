package com.yapp.bol.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.OAuthApiItem
import com.yapp.bol.domain.usecase.login.KakaoLoginUseCase
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KakaoTestViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : ViewModel() {

    private val _accessToken = MutableLiveData(EMPTY_STRING)
    val accessToken = _accessToken

    fun loginTest(token: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(token).collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = ::updateAccessToken,
                    error = { throwable -> throw throwable }
                )
            }
        }
    }

    private fun updateAccessToken(item: OAuthApiItem) {
        accessToken.value = item.accessToken
    }
}
