package com.yapp.bol.presentation.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.Constants.ACCESS_TOKEN
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ActivityLoginBinding
import com.yapp.bol.presentation.utils.Constant
import com.yapp.bol.presentation.view.login.auth.KakaoTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = intent.getStringExtra(KakaoTestActivity.ACCESS_TOKEN) ?: Constant.EMPTY_STRING
        val bundle = Bundle()
        bundle.putString(ACCESS_TOKEN, accessToken)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment) as NavHostFragment
        navHostFragment.findNavController().setGraph(R.navigation.login_nav_graph, bundle)
    }
}
