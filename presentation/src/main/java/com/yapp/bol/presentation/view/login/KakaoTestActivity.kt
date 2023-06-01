package com.yapp.bol.presentation.view.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.R
import com.kakao.sdk.common.KakaoSdk
import com.yapp.bol.presentation.BuildConfig
import com.yapp.bol.presentation.databinding.ActivityKakaoTestBinding
import com.yapp.bol.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KakaoTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKakaoTestBinding

    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var kakaoClient: UserApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, KAKAO_API_KEY)
        kakaoClient = UserApiClient.instance
    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
    }
}