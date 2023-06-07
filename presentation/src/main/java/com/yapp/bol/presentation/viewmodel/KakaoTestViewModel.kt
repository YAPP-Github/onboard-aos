package com.yapp.bol.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.usecase.login.KakaoLoginUseCase
import com.yapp.bol.domain.utils.ErrorType
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KakaoTestViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : ViewModel(), RemoteErrorEmitter {

    private val _accessToken = MutableLiveData(EMPTY_STRING)
    val accessToken = _accessToken

    fun loginTest(token: String) = viewModelScope.launch {
        val response: MockApiItem? = kakaoLoginUseCase.execute(this@KakaoTestViewModel, token)
        _accessToken.value = response?.accessToken ?: EMPTY_STRING
    }

    override fun onError(msg: String) {
        Log.d("MOCK TEST", msg)
    }

    override fun onError(errorType: ErrorType) {
        Log.d("MOCK TEST", errorType.name)
    }
}
