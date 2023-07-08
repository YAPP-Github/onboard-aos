package com.yapp.bol.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.databinding.ActivityMainBinding
import com.yapp.bol.presentation.view.login.GoogleTestActivity
import com.yapp.bol.presentation.view.login.KakaoTestActivity
import com.yapp.bol.presentation.view.login.NaverTestActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
