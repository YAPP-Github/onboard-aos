package com.yapp.bol.presentation.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.kakao.sdk.auth.Constants.ACCESS_TOKEN
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ActivityMainBinding
import com.yapp.bol.presentation.utils.Constant
import com.yapp.bol.presentation.view.login.auth.KakaoTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra(KakaoTestActivity.ACCESS_TOKEN) ?: Constant.EMPTY_STRING
        val bundle = Bundle()
        bundle.putString(ACCESS_TOKEN, accessToken)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
    }
}
