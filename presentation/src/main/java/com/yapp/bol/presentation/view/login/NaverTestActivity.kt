package com.yapp.bol.presentation.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.yapp.bol.presentation.BuildConfig
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.showToast
import com.yapp.bol.presentation.view.group.search.GroupSearchActivityTest
import com.yapp.bol.presentation.viewmodel.login.LoginType
import com.yapp.bol.presentation.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class NaverTestActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_naver_test)

        loginNaver()
        subscribeObservables()
    }

    private fun setUpNaverLogin() {
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_CLIENT_ID,
            clientName = BuildConfig.NAVER_CLIENT_NAME,
            clientSecret = BuildConfig.NAVER_CLIENT_SECRET,
        )
    }

    private fun loginNaver() {
        setUpNaverLogin()

        val oauthLoginCallback = object : OAuthLoginCallback {

            override fun onSuccess() {
                NaverIdLoginSDK.getAccessToken()?.let { viewModel.login(LoginType.NAVER, it) }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                showToast("fail $message ")
            }

            override fun onError(errorCode: Int, message: String) {
                showToast("error $message")
            }
        }

        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
    }

    private fun subscribeObservables() {
        viewModel.loginResult.filterNotNull().collectWithLifecycle(this) {
            val intent = Intent(this@NaverTestActivity, GroupSearchActivityTest::class.java)
            intent.putExtra(KakaoTestActivity.ACCESS_TOKEN, it.accessToken)
            startActivity(intent)
        }
    }
}
