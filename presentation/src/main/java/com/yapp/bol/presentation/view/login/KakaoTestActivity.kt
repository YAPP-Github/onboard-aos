package com.yapp.bol.presentation.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.R

class KakaoTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_test)
    companion object {
        const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY
    }
}