package com.yapp.bol.presentation.view.match

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
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

        binding.ibBackButton.setOnClickListener {
            if(viewModel.currentPage.value == GAME_SELECT) finish()
            else binding.navHostFragment.findNavController().popBackStack()
        }

        viewModel.updateGroupId(groupId)
        viewModel.toolBarTitle.observe(this) { title ->
            binding.tvPageName.text = title
        }
        viewModel.currentPage.observe(this) {
            when(it) {
                GAME_SELECT -> {
                    binding.viewPage1.setOrangeColor(true)
                    binding.viewPage2.setOrangeColor(false)
                    binding.viewPage3.setOrangeColor(false)
                }
                MEMBER_SELECT -> {
                    binding.viewPage1.setOrangeColor(true)
                    binding.viewPage2.setOrangeColor(true)
                    binding.viewPage3.setOrangeColor(false)
                }
                GAME_RESULT -> {
                    binding.viewPage1.setOrangeColor(true)
                    binding.viewPage2.setOrangeColor(true)
                    binding.viewPage3.setOrangeColor(true)
                }
            }
        }
    }

    private fun View.setOrangeColor(isCheck: Boolean) {
        val color = if(isCheck)  R.color.Orange_5 else  R.color.Orange_1
        this.setBackgroundColor(ContextCompat.getColor(this@MatchActivity,color))
    }
    companion object {
        /* 추후 enum class 로 변경 예정*/
        const val GUEST = "GUEST"
        const val GAME_SELECT = 1
        const val MEMBER_SELECT = 2
        const val GAME_RESULT = 3
    }
}
