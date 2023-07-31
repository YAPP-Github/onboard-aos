package com.yapp.bol.presentation.view.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.setting.GetSettingTermsUseCase
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val getSettingTermsUseCase: GetSettingTermsUseCase,
) : ViewModel() {

    private val _termStateFlow = MutableStateFlow<TermsUrl>(TermsUrl("", ""))
    val termStateFlow: StateFlow<TermsUrl> = _termStateFlow

    init {
        getTerms()
    }

    fun getTerms() {
        viewModelScope.launch {
            getSettingTermsUseCase.invoke().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data ->
                        var privacy = ""
                        var service = ""
                        data.map { item ->
                            if (item.code.startsWith("SERVICE")) {
                                service = item.url
                            } else {
                                privacy = item.url
                            }
                        }
                        _termStateFlow.value = TermsUrl(privacy, service)
                    },
                )
            }
        }
    }

    data class TermsUrl(
        val privacy: String,
        val service: String,
    )
}
