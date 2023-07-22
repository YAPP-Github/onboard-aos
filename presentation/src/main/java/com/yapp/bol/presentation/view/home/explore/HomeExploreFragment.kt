package com.yapp.bol.presentation.view.home.explore

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.PagingData
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentHomeExploreBinding
import com.yapp.bol.presentation.utils.loseFocusOnAction
import com.yapp.bol.presentation.utils.setNavigationBarColor
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.utils.textChangesToFlow
import com.yapp.bol.presentation.utils.withLoadStateAdapters
import com.yapp.bol.presentation.view.group.search.GroupListAdapter
import com.yapp.bol.presentation.view.group.search.GroupListLoadStateAdapter
import com.yapp.bol.presentation.view.group.search.GroupSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.yapp.bol.designsystem.R as designsystemR

@AndroidEntryPoint
class HomeExploreFragment : BaseFragment<FragmentHomeExploreBinding>(R.layout.fragment_home_explore) {

    private val viewModel: GroupSearchViewModel by viewModels()

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        binding.viewGroupListLoading.root.visibility = View.GONE
        setAdapter()
        setBackButton()
        setStatusBarColor(this@HomeExploreFragment.requireActivity(), designsystemR.color.Gray_1, isIconBlack = true)
        setNavigationBarColor(this@HomeExploreFragment.requireActivity(), designsystemR.color.Gray_1)
    }

    private fun setAdapter() {
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

    private fun FragmentHomeExploreBinding.initSearchView(adapter: GroupListAdapter) {
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

    private fun FragmentHomeExploreBinding.initEditText(adapter: GroupListAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            val editTextFlow = viewGroupSearch.etGroupSearch.textChangesToFlow()
            val debounceDuration = 500L

            editTextFlow
                .onEach {
                    val isTyping = !(it.isNullOrBlank() || it.isEmpty())
                    viewGroupSearch.btnSearchRight.setImageButtonByState(isTyping)
                }
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
    private fun ImageView.setImageButtonByState(isTyping: Boolean) =
        viewLifecycleOwner.lifecycleScope.launch {
            when (isTyping) {
                true -> setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, designsystemR.drawable.ic_x)
                )

                false -> setImageDrawable(
                    AppCompatResources.getDrawable(binding.root.context, designsystemR.drawable.ic_search)
                )
            }
        }

    private fun setBackButton() {
        binding.btnBack.setOnClickListener {
            binding.root.findNavController().popBackStack()
        }
    }
}
