package com.yapp.bol.presentation.view.group.search

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentGroupSearchBinding
import com.yapp.bol.presentation.utils.loseFocusOnAction
import com.yapp.bol.presentation.utils.textChangesToFlow
import com.yapp.bol.presentation.utils.withLoadStateAdapters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GroupSearchFragment : BaseFragment<FragmentGroupSearchBinding>(R.layout.fragment_group_search) {

    private val viewModel: GroupSearchViewModel by activityViewModels()

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        binding.lifecycleOwner = this@GroupSearchFragment
        binding.viewGroupListLoading.root.visibility = View.GONE
        bindView()
    }

    private fun bindView() {
        val adapter = GroupListAdapter()
        initPaging(adapter)
        binding.initSearchView(adapter)
        adapter.addOnPagesUpdatedListener {
            binding.viewGroupListLoading.root.visibility = View.GONE
        }
    }

    private fun initPaging(adapter: GroupListAdapter) {
        val concatAdapter = adapter.withLoadStateAdapters(
            header = GroupListLoadStateAdapter { adapter.retry() },
            footer = GroupListLoadStateAdapter { adapter.retry() }
        )
        binding.rvGroupList.adapter = concatAdapter
    }

    // 상단 search view 초기화 관련 작업
    private fun FragmentGroupSearchBinding.initSearchView(adapter: GroupListAdapter) {
        initEditText(adapter)

        val editText = viewGroupSearch.etGroupSearch
        val rightDrawableBtn = viewGroupSearch.btnSearchRight

        rightDrawableBtn.setOnClickListener {
            if (!editText.text.isNullOrEmpty() || !editText.text.isNullOrBlank()) {
                editText.text.clear()
            }
        }

        editText.loseFocusOnAction(EditorInfo.IME_ACTION_SEARCH, this.root.context)

        binding.viewGroupSearch.btnCreateGroup.setOnClickListener {
            // TODO : create transition
        }
    }

    // search view의 edittext 세팅
    private fun FragmentGroupSearchBinding.initEditText(adapter: GroupListAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            val editTextFlow = viewGroupSearch.etGroupSearch.textChangesToFlow()
            val debounceDuration = 500L

            editTextFlow
                .onEach {
                    val isTyping = !(it.isNullOrBlank() || it.isEmpty())
                    viewGroupSearch.btnSearchRight.setImageButtonByState(isTyping)
                }
                .launchIn(this)

            editTextFlow
                .debounce(debounceDuration)
                .onEach {
                    adapter.searchByKeyword(it.toString())
                }
                .launchIn(this)
        }
    }

    private fun GroupListAdapter.searchByKeyword(keyword: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            submitData(PagingData.empty())
            viewModel.searchGroup(keyword).collectLatest {
                submitData(it)
            }
        }
    }

    // search view의 edittext typing 여부에 따른 우측 아이콘 변경
    private fun ImageButton.setImageButtonByState(isTyping: Boolean) =
        viewLifecycleOwner.lifecycleScope.launch {
            when (isTyping) {
                true -> setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_x)
                )
                false -> setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_search)
                )
            }
        }
}
