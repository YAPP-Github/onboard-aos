package com.yapp.bol.presentation.view.group

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ActivityGroupBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.view.group.join.GroupJoinViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupActivity : AppCompatActivity() {

    private val viewModel: GroupJoinViewModel by viewModels()
    val binding: ActivityGroupBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_group)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribeObservales()
    }

    private fun subscribeObservales() {
        viewModel.loading.collectWithLifecycle(this) { (isLoading, message) ->
            binding.loadingLayout.isVisible = isLoading
            binding.tvLoadingTitle.text = message
        }
    }
}
