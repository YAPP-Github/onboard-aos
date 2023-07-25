package com.yapp.bol.presentation.view.home.rank

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentHomeRankBinding
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel
import com.yapp.bol.presentation.model.UserRankUiModel
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.copyToClipboard
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.utils.showToast
import com.yapp.bol.presentation.view.home.HomeUiState
import com.yapp.bol.presentation.view.home.rank.UserRankViewModel.Companion.RV_SELECTED_POSITION_RESET
import com.yapp.bol.presentation.view.home.rank.game.UserRankGameAdapter
import com.yapp.bol.presentation.view.home.rank.game.UserRankGameLayoutManager
import com.yapp.bol.presentation.view.home.rank.group_info.DrawerGroupInfoAdapter
import com.yapp.bol.presentation.view.home.rank.user.UserRankAdapter
import com.yapp.bol.presentation.view.match.MatchActivity
import dagger.hilt.android.AndroidEntryPoint
import com.yapp.bol.designsystem.R as designsystemR

@AndroidEntryPoint
class HomeRankFragment : BaseFragment<FragmentHomeRankBinding>(R.layout.fragment_home_rank) {
    private val viewModel: UserRankViewModel by viewModels()

    private lateinit var drawerGroupInfoAdapter: DrawerGroupInfoAdapter
    private lateinit var userRankGameAdapter: UserRankGameAdapter

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        initViewModel()

        setHomeRecyclerView()
        setDrawer()
        observeGameAndGroupUiState(drawerGroupInfoAdapter, userRankGameAdapter)

        setStatusBarColor(this@HomeRankFragment.requireActivity(), designsystemR.color.Gray_15, isIconBlack = false)

        scrollCenterWhenUserRankTouchDown()

        setFloatingButton()
    }

    private fun initViewModel() {
        // TODO : Home 넘어오는 값으로 변경 필요
        viewModel.groupId = 90
    }

    private fun setHomeRecyclerView() {
        setGameAdapter()
        setUserAdapter()
    }

    private fun setGameAdapter() {
        val onClick: (position: Int, gameId: Long) -> Unit = { position, gameId ->
            viewModel.setGameItemSelected(position)
            viewModel.gameId = gameId
        }
        val scrollAnimation: () -> Unit = {
            binding.rvGameList.smoothScrollToPosition(viewModel.getGameItemSelectedPosition())
        }
        userRankGameAdapter = UserRankGameAdapter(onClick, scrollAnimation)

        binding.rvGameList.apply {
            layoutManager = UserRankGameLayoutManager(
                binding.rvGameList.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = userRankGameAdapter
        }
    }

    private fun setUserAdapter() {
        val userRankAdapter = UserRankAdapter()
        binding.rvUserRank.adapter = userRankAdapter
        observeUserRankUiState(userRankAdapter)
    }

    private fun setDrawer() {
        setDrawerOpen()
        setDrawerAdapter()
        bindExploreButton()
        bindSettingButton()
    }

    private fun setDrawerOpen() {
        binding.btnGroupName.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun setDrawerAdapter() {
        val otherGroupOnClick: (Long) -> Unit = { id ->
            viewModel.apply {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                groupId = id
            }
        }

        val copyButtonOnClick: (String) -> Unit = {
            it.copyToClipboard(binding.root.context)
            showToastForAndroid13Below()
        }

        drawerGroupInfoAdapter = DrawerGroupInfoAdapter(
            otherGroupOnClick = otherGroupOnClick,
            copyButtonOnClick = copyButtonOnClick,
        )
        binding.rvGroupInfo.adapter = drawerGroupInfoAdapter
    }

    private fun bindExploreButton() {
        binding.viewFooter.llBtnExplore.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_homeRankFragment_to_homeExploreFragment)
        }
    }

    private fun bindSettingButton() {
        binding.viewFooter.btnSetting.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_homeRankFragment_to_settingFragment)
        }
    }

    private fun setCurrentGroupInfo(currentGroupInfo: DrawerGroupInfoUiModel.CurrentGroupInfo) {
        binding.apply {
            currentGroupInfo.groupDetailItem.let { item ->
                viewHeader.tvGroupName.text = item.name
                tvGroupName.text = item.name
                viewRankNotFound.tvCode.text = item.accessCode
                viewRankNotFound.btnCodeCopy.setOnClickListener {
                    item.accessCode.copyToClipboard(root.context)
                    showToastForAndroid13Below()
                }
            }
        }
    }

    private val userRankSnackBar: SnackBarHomeReload by lazy {
        SnackBarHomeReload.make(
            view = binding.root,
            onClick = { viewModel.fetchUserList() }
        )
    }

    private fun observeUserRankUiState(userRankAdapter: UserRankAdapter) {
        viewModel.userUiState.collectWithLifecycle(this) { uiState ->
            fun List<UserRankUiModel>.isNoRank(): Boolean = this.isEmpty()

            when (uiState) {
                is HomeUiState.Success -> {
                    userRankSnackBar.dismiss()
                    val isNoRank: Boolean = uiState.data.isNoRank()

                    binding.apply {
                        if (isSelectedPositionValid()) {
                            rvGameList.smoothScrollToPosition(viewModel.getGameItemSelectedPosition())
                        }
                        viewRankLoading.visibility = View.GONE
                        viewRankNotFound.root.visibility = if (isNoRank) { View.VISIBLE } else { View.GONE }
                        rvUserRank.visibility = if (isNoRank) { View.GONE } else { View.VISIBLE }
                    }

                    if (!isNoRank) { userRankAdapter.submitList(uiState.data) }
                }

                is HomeUiState.Loading -> {
                    binding.apply {
                        rvUserRank.visibility = View.GONE
                        viewRankNotFound.root.visibility = View.GONE
                        viewRankLoading.visibility = View.VISIBLE
                    }
                }

                is HomeUiState.Error -> {
                    userRankSnackBar.show()
                }
            }
        }
    }

    private val gameSnackBar: SnackBarHomeReload by lazy {
        SnackBarHomeReload.make(
            view = binding.root,
            onClick = { viewModel.fetchAll() }
        )
    }

    private fun observeGameAndGroupUiState(
        drawerGroupInfoAdapter: DrawerGroupInfoAdapter,
        userRankGameAdapter: UserRankGameAdapter,
    ) {
        viewModel.gameAndGroupUiState.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is HomeUiState.Success -> {
                    uiState.data.group.map { uiModel ->
                        if (uiModel is DrawerGroupInfoUiModel.CurrentGroupInfo) {
                            setCurrentGroupInfo(uiModel)
                        }
                    }
                    drawerGroupInfoAdapter.submitList(uiState.data.group)
                    userRankGameAdapter.submitList(uiState.data.game)

                    binding.btnGroupName.visibility = View.VISIBLE
                    binding.loadingGroupName.visibility = View.INVISIBLE
                    binding.rvGameList.visibility = View.VISIBLE

                    gameSnackBar.dismiss()
                }

                is HomeUiState.Loading -> {
                    binding.btnGroupName.visibility = View.INVISIBLE
                    binding.loadingGroupName.visibility = View.VISIBLE
                    binding.rvGameList.visibility = View.GONE
                }

                is HomeUiState.Error -> {
                    gameSnackBar.show()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun scrollCenterWhenUserRankTouchDown() {
        binding.rvUserRank.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState != SCROLL_STATE_IDLE && isSelectedPositionValid()) {
                    binding.rvGameList.smoothScrollToPosition(viewModel.getGameItemSelectedPosition())
                }
            }
        })
    }

    private fun showToastForAndroid13Below() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
            binding.root.context.showToast(R.string.copy_access_code, Toast.LENGTH_SHORT)
        }
    }

    private fun isSelectedPositionValid(): Boolean {
        return RV_SELECTED_POSITION_RESET != viewModel.getGameItemSelectedPosition()
    }

    private fun setFloatingButton() {
        binding.btnCreateGroup.setOnClickListener {
            val intent = Intent(requireActivity(), MatchActivity::class.java)
            intent.putExtra(GROUP_ID, viewModel.groupId.toInt())
            startActivity(intent)
        }
    }
    companion object {
        const val GROUP_ID = "Group Id"
    }
}
