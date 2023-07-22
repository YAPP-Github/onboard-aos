package com.yapp.bol.presentation.view.login.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.yapp.bol.presentation.BuildConfig
import com.yapp.bol.presentation.databinding.ActivityKakaoTestBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.view.login.LoginActivity
import com.yapp.bol.presentation.viewmodel.login.LoginType
import com.yapp.bol.presentation.viewmodel.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class KakaoTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKakaoTestBinding

    private val viewModel: LoginViewModel by viewModels()

    private val kakaoClient: UserApiClient by lazy { UserApiClient.instance }

    private val kakaoOAuthCallBack: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
        token?.let { viewModel.login(LoginType.KAKAO, token.accessToken) }
    }
    private val isKakaoTalkInstalled
        get() = kakaoClient.isKakaoTalkLoginAvailable(this)

    private val isClientErrorCancelled: (Throwable?) -> Boolean = { error ->
        error is ClientError && error.reason == ClientErrorCause.Cancelled
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, KAKAO_API_KEY)
        kakaoLogin()
        subscribeObservables()
    }
    private fun kakaoLogin() {
        if (isKakaoTalkInstalled) {
            handleKakaoTalkLoginResult()
        } else {
            kakaoClient.loginWithKakaoAccount(this, callback = kakaoOAuthCallBack)
        }
    }
    private fun handleKakaoTalkLoginResult() {
        kakaoClient.loginWithKakaoTalk(this) { token, error ->
            if (isClientErrorCancelled(error)) return@loginWithKakaoTalk
            error?.let { kakaoClient.loginWithKakaoAccount(this, callback = kakaoOAuthCallBack) }
            token?.let { viewModel.login(LoginType.KAKAO, token.accessToken) }
        }
    }
    private fun subscribeObservables() {
        viewModel.loginResult.filterNotNull().collectWithLifecycle(this) {
            if (it.accessToken.isEmpty()) return@collectWithLifecycle
            val intent = Intent(this@KakaoTestActivity, LoginActivity::class.java)
            intent.putExtra(ACCESS_TOKEN, it.accessToken)
            startActivity(intent)
        }
    }
    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
        const val ACCESS_TOKEN = "Access Token"
    }
}
