package com.yapp.bol.presentation.view.login

import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityLoginBinding
import com.yapp.bol.presentation.firebase.GA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    override fun getScreenName(): String = GA.Screen.LOGIN
}
