package com.yapp.bol.presentation.view.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    var termsServiceState = false
    var termsPrivacyState = false
    var termsMarketingState = false

    fun updateTermsServiceState(state: Boolean) {
        termsServiceState = state
    }

    fun updateTermsPrivacyState(state: Boolean) {
        termsPrivacyState = state
    }

    fun updateTermsMarketingState(state: Boolean) {
        termsMarketingState = state
    }

    fun checkedRequired(): Boolean {
        return termsServiceState && termsPrivacyState
    }
}
