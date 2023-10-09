package com.yapp.bol.presentation.view.login.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.usecase.login.SplashUseCase
import com.yapp.bol.presentation.model.OnBoardingUiModel
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashUseCase: SplashUseCase,
) : ViewModel() {

    private val _animationState = MutableLiveData(false)
    val animationState: LiveData<Boolean> = _animationState

    private val _onBoardingState = MutableLiveData<OnBoardingUiModel>()
    val onBoardingState: LiveData<OnBoardingUiModel> = _onBoardingState

    private val _networkState = MutableLiveData(false)
    val networkState: LiveData<Boolean> = _networkState

    private val _upgradeState = MutableLiveData(false)
    val upgradeState: LiveData<Boolean> = _upgradeState

    private val _onBoardingErrorState = MutableLiveData(false)
    val onBoardingErrorState: LiveData<Boolean> = _onBoardingErrorState

    init {
        viewModelScope.launch {
            delay(2000)
            _animationState.value = true
        }
    }

    fun getMyGroupList() {
        viewModelScope.launch {
            splashUseCase.getOnBoard().collectLatest {
                checkedApiResult(
                    apiResult = it,
                    success = { data -> updateOnBoarding(OnBoardingUiModel(data.onBoarding, data.mainGroupId)) },
                    error = { item ->
                        when (item.code) {
                            "Auth004" -> _onBoardingErrorState.value = true
                            "FORCE_UPDATE" -> _upgradeState.value = true
                            else -> _networkState.value = true
                        }
                    },
                )
            }
        }
    }

    private fun updateOnBoarding(onBoardingUiModel: OnBoardingUiModel) {
        _onBoardingState.value = onBoardingUiModel
    }
}
