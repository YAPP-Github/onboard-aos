package com.yapp.bol.presentation.view.group.search

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseActivity
import com.yapp.bol.presentation.databinding.ActivityGroupSearchTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * 테스트용으로 만들어둔 파일입니다.
 */
@AndroidEntryPoint
class GroupSearchActivityTest : BaseActivity<ActivityGroupSearchTestBinding>(R.layout.activity_group_search_test) {
    override val viewModel: GroupSearchViewModel by viewModels()
    private val adapter by lazy { GroupListAdapter() }

    override fun initViewModel(viewModel: ViewModel) {
        binding.lifecycleOwner = this@GroupSearchActivityTest
        binding.viewmodel = this.viewModel
    }

    override fun onCreateAction() {
        initPagingFlow()
        binding.viewGroupSearch
    }

    private fun initPagingFlow() {
        binding.rvGroupList.adapter = adapter.withLoadStateFooter(
            footer = GroupListLoadStateAdapter { adapter.retry() }
        )

        lifecycleScope.launch() {
            val data = viewModel.searchGroup("gdfasdf").first()
            adapter.submitData(data)
        }
    }
}
