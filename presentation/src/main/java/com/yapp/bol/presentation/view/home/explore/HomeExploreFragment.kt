package com.yapp.bol.presentation.view.home.explore

import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.PagingData
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentHomeExploreBinding
import com.yapp.bol.presentation.firebase.GA
import com.yapp.bol.presentation.utils.createSmoothColorAnimator
import com.yapp.bol.presentation.utils.loseFocusOnAction
import com.yapp.bol.presentation.utils.navigateFragment
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.utils.textChangesToFlow
import com.yapp.bol.presentation.utils.withLoadStateAdapters
import com.yapp.bol.presentation.view.group.NewGroupActivity
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
        setStatusBarColor(this@HomeExploreFragment.requireActivity(), designsystemR.color.Gray_2, isIconBlack = true)
    }


    private fun setAdapter() {
        val adapter = GroupListAdapter(
            showJoinGroupDialog = {
                view?.findNavController()?.navigateFragment(
                    R.id.action_homeExploreFragment_to_groupJoinFragment,
                    "groupId" to it.id
                )
            },
            changeButtonColor = {
                val textColor = ContextCompat.getColor(binding.root.context, designsystemR.color.Gray_1)
                val afterBgColor = ContextCompat.getColor(binding.root.context, designsystemR.color.Orange_9)
                val beforeBgColor = ContextCompat.getColor(binding.root.context, designsystemR.color.Gray_5)
                setCreateGroupButtonStyleWithAnimation(
                    textColor = textColor,
                    beforeBgColor = beforeBgColor,
                    afterBgColor = afterBgColor,
                )
            }
        )
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
            startActivity(Intent(requireContext(), NewGroupActivity::class.java))
        }
    }

    private fun FragmentHomeExploreBinding.initEditText(adapter: GroupListAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            val editTextFlow = viewGroupSearch.etGroupSearch.textChangesToFlow()
            val debounceDuration = 500L

            val textColor = ContextCompat.getColor(binding.root.context, designsystemR.color.Gray_10)
            val backgroundColor = ContextCompat.getColor(binding.root.context, designsystemR.color.Gray_5)

            editTextFlow
                .onEach {
                    val isTyping = !(it.isNullOrBlank() || it.isEmpty())
                    viewGroupSearch.btnSearchRight.setImageButtonByState(isTyping)
                }
                .debounce(debounceDuration)
                .onEach {
                    setCreateGroupButtonStyle(
                        textColor = textColor,
                        backgroundColor = backgroundColor
                    )
                    adapter.searchByKeyword(it.toString())
                }
                .launchIn(this)
        }
    }

    private fun setCreateGroupButtonStyle(
        textColor: Int,
        backgroundColor: Int
    ) {
        binding.viewGroupSearch.btnCreateGroup.apply {
            setTextColor(textColor)
            backgroundTintList = ColorStateList.valueOf(backgroundColor)
        }
    }

    private fun setCreateGroupButtonStyleWithAnimation(
        textColor: Int,
        beforeBgColor: Int,
        afterBgColor: Int
    ) {
        binding.viewGroupSearch.btnCreateGroup.apply {
            setTextColor(textColor)
            createSmoothColorAnimator(beforeBgColor, afterBgColor, 200L).start()
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

    override fun getScreenName(): String = GA.Screen.HOME_EXPLORE
}
