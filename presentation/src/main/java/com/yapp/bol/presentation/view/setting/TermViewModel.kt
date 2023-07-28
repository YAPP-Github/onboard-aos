package com.yapp.bol.presentation.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.domain.usecase.login.LoginUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _termStateFlow = MutableStateFlow<List<TermsItem>>(listOf())
    val termStateFlow: StateFlow<List<TermsItem>> = _termStateFlow

    fun getTerms() {
        viewModelScope.launch {
            loginUseCase.getTerms().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> _termStateFlow.value = data.contents },
                )
            }

        }
    }
}
