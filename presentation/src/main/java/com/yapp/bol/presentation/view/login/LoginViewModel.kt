package com.yapp.bol.presentation.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.bol.domain.model.TermsItem
import com.yapp.bol.domain.model.TermsList
import com.yapp.bol.domain.usecase.login.LoginUseCase
import com.yapp.bol.presentation.model.OnBoardingUiModel
import com.yapp.bol.presentation.utils.checkedApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _onboardState = MutableLiveData<OnBoardingUiModel?>(null)
    val onboardState: LiveData<OnBoardingUiModel?> = _onboardState

    private val _termsList = MutableLiveData<List<TermsItem>?>(null)
    val termsList: LiveData<List<TermsItem>?> = _termsList

    private val _dialogState = MutableLiveData(false)
    val dialogState: LiveData<Boolean> = _dialogState

    private val _isEnableSignUp = MutableLiveData(false)
    val isEnableSignUp: LiveData<Boolean> = _isEnableSignUp

    init {
        getOnBoard()
    }

    private fun getOnBoard() {
        viewModelScope.launch {
            loginUseCase.getOnBoard().collectLatest {
                checkedApiResult(apiResult = it, success = { data -> updateOnBoard(data.onBoarding, data.mainGroupId) })
            }
        }
    }

    fun getTerms() {
        viewModelScope.launch {
            loginUseCase.getTerms().collectLatest {
                checkedApiResult(apiResult = it, success = ::updateTermList)
            }
        }
    }

    fun postTerms() {
        viewModelScope.launch {
            loginUseCase.postTerms(getAgree(), getDisagree())
        }
    }

    fun updateLike(position: Int, isChecked: Boolean) {
        _termsList.value = termsList.value?.mapIndexed { index, termsItem ->
            if (index == position) {
                termsItem.copy(isChecked = isChecked)
            } else {
                termsItem
            }
        }
        checkedRequired()
    }

    fun updateAllLike(isChecked: Boolean) {
        _termsList.value = termsList.value?.map { it.copy(isChecked = isChecked) }
        checkedRequired()
    }

    fun updateDialogState(state: Boolean) {
        _dialogState.value = state
    }

    fun checkedTermsAll(state: Boolean): Boolean {
        return termsList.value?.find { it.isChecked != state } == null
    }

    private fun updateTermList(data: TermsList) {
        updateDialogState(true)
        _termsList.value = data.contents
    }

    private fun updateOnBoard(data: List<String>, mainGroupId: Int?) {
        _onboardState.value = OnBoardingUiModel(data, mainGroupId)
    }

    private fun getAgree(): List<String> {
        return termsList.value
            ?.filter { it.isChecked }
            ?.map { it.code } ?: listOf()
    }

    private fun getDisagree(): List<String> {
        return termsList.value
            ?.filter { it.isChecked.not() }
            ?.map { it.code } ?: listOf()
    }

    private fun checkedRequired() {
        val result = termsList.value?.find { it.isRequired && it.isChecked.not() }
        _isEnableSignUp.value = result == null
    }
}
