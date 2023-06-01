package com.yapp.bol.presentation.view.login

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
import com.yapp.bol.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKakaoTestBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var kakaoClient: UserApiClient

    private val kakaoCallBack: (OAuthToken?, Throwable?) -> Unit = { token, _ ->
        token?.let { mainViewModel.loginTest(token.accessToken) }
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
        kakaoClient = UserApiClient.instance
        kakaoLogin()
    }

    private fun kakaoLogin() {
        if (isKakaoTalkInstalled) {
            handleKakaoTalkLoginResult()
        } else {
            kakaoClient.loginWithKakaoAccount(this, callback = kakaoCallBack)
        }
    }

    private fun handleKakaoTalkLoginResult() {
        kakaoClient.loginWithKakaoTalk(this) { token, error ->
            if (isClientErrorCancelled(error)) return@loginWithKakaoTalk
            error?.let { kakaoClient.loginWithKakaoAccount(this, callback = kakaoCallBack) }
            token?.let { mainViewModel.loginTest(token.accessToken) }
        }
    }

    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
    }
}