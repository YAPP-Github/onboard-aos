package com.yapp.bol.presentation.view.match

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityMatchBinding
import com.yapp.bol.presentation.utils.createScaleWidthAnimator
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.view.home.rank.HomeRankFragment.Companion.GROUP_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MatchActivity : BaseActivity<ActivityMatchBinding>(R.layout.activity_match) {

    private val viewModel: MatchViewModel by viewModels()

    override fun onCreateAction() {
        super.onCreateAction()
        val groupId = intent.getIntExtra(GROUP_ID, 0)

        binding.ibBackButton.setOnClickListener {
            if (viewModel.pageState.value == GAME_SELECT) finish()
            else binding.navHostFragment.findNavController().popBackStack()
        }

        viewModel.updateGroupId(groupId)
        viewModel.toolBarTitle.observe(this) { title ->
            binding.tvPageName.text = title
        }
        viewModel.pageState.observe(this) {
            val isNext = viewModel.currentPage < it
            when (it) {
                GAME_SELECT -> {
                    if (isNext) binding.viewPage1.startAnimator(true) else binding.viewPage2.startAnimator(false)
                }

                MEMBER_SELECT -> {
                    if (isNext) binding.viewPage2.startAnimator(true) else binding.viewPage3.startAnimator(false)
                }

                GAME_RESULT -> {
                    binding.viewPage3.startAnimator(true)
                }
            }
            viewModel.updateCurrentPage(it)
        }
    }

    private fun View.startAnimator(isCheck: Boolean?) {
        if (isCheck == null) return
        val width = getMaxWidth() / 3
        if (isCheck) {
            this.createScaleWidthAnimator(startWidth = 0, endWidth = width)
        } else {
            this.createScaleWidthAnimator(startWidth = width, endWidth = 0)
        }
    }

    private fun getMaxWidth(): Int {
        val display = applicationContext?.resources?.displayMetrics
        return (display?.widthPixels ?: 0) - applicationContext.dpToPx(24)
    }

    companion object {
        /* 추후 enum class 로 변경 예정*/
        const val GUEST = "GUEST"
        const val GAME_SELECT = 1
        const val MEMBER_SELECT = 2
        const val GAME_RESULT = 3
    }
}
