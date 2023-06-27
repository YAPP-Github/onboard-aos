package com.yapp.bol.presentation.view.match

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yapp.bol.presentation.databinding.ActivityMatchBinding
import com.yapp.bol.presentation.view.group.NewGroupActivity.Companion.GROUP_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchBinding
    private val viewModel: MatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val groupId = intent.getIntExtra(GROUP_ID, 0)
        viewModel.updateGroupId(groupId)
        viewModel.toolBarTitle.observe(this) { title ->
            binding.tvPageName.text = title
        }
    }
}
