package com.yapp.bol.presentation.view.group

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ActivityGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupActivity : AppCompatActivity() {

    val binding: ActivityGroupBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_group)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
