package com.yapp.bol.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.databinding.ActivityMainBinding
import com.yapp.bol.presentation.view.login.GoogleTestActivity
import com.yapp.bol.presentation.view.login.KakaoTestActivity
import com.yapp.bol.presentation.view.login.NaverTestActivity
import com.yapp.bol.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonListener()
    }

    private fun setButtonListener() {
        binding.btnGoogle.setOnClickListener {
            Intent(this@MainActivity, GoogleTestActivity::class.java).also { startActivity(it) }
        }

        binding.btnKakao.setOnClickListener {
            Intent(this@MainActivity, KakaoTestActivity::class.java).also { startActivity(it) }
        }

        binding.btnNaver.setOnClickListener {
            Intent(this@MainActivity, NaverTestActivity::class.java).also { startActivity(it) }
        }
    }
}