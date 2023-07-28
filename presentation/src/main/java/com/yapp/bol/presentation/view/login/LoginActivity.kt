package com.yapp.bol.presentation.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.databinding.ActivityLoginBinding
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
    }
}
