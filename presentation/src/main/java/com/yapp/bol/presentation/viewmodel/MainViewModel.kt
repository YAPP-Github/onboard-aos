package com.yapp.bol.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.MockApiItem
import com.yapp.bol.domain.usecase.login.KakaoLoginUseCase
import com.yapp.bol.domain.utils.ErrorType
import com.yapp.bol.domain.utils.RemoteErrorEmitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
) : ViewModel(), RemoteErrorEmitter {
    fun loginTest() = viewModelScope.launch {
        val response: MockApiItem? = kakaoLoginUseCase.execute(this@MainViewModel, "SUCCESSid")
        Log.d("MOCK TEST", response.toString())
    }

    override fun onError(msg: String) {
        Log.d("MOCK TEST", msg)
    }

    override fun onError(errorType: ErrorType) {
        Log.d("MOCK TEST", errorType.name)
    }
}
