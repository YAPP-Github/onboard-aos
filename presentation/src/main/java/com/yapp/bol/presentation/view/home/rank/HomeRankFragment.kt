package com.yapp.bol.presentation.view.home.rank

import android.annotation.SuppressLint
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
import com.yapp.bol.presentation.utils.config.HomeConfig
import com.yapp.bol.presentation.utils.copyToClipboard
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.utils.showToast
import com.yapp.bol.presentation.view.home.HomeUiState
import com.yapp.bol.presentation.view.home.rank.UserRankViewModel.Companion.RV_SELECTED_POSITION_RESET
import com.yapp.bol.presentation.view.home.rank.game.UserRankGameAdapter
import com.yapp.bol.presentation.view.home.rank.game.UserRankGameLayoutManager
import com.yapp.bol.presentation.view.home.rank.group_info.DrawerGroupInfoAdapter
import com.yapp.bol.presentation.view.home.rank.user.UserRankAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.yapp.bol.designsystem.R as designsystemR

@AndroidEntryPoint
class HomeRankFragment : BaseFragment<FragmentHomeRankBinding>(R.layout.fragment_home_rank) {
    private val viewModel: UserRankViewModel by viewModels()

    private var groupId: Long = 90
    private var gameId: Long = 0
    private lateinit var drawerGroupInfoAdapter: DrawerGroupInfoAdapter
    private lateinit var userRankGameAdapter: UserRankGameAdapter

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        initViewModel()

        setHomeRecyclerView()
        setDrawer()
        observeGameUiState(drawerGroupInfoAdapter, userRankGameAdapter)

        setStatusBarColor(this@HomeRankFragment.requireActivity(), designsystemR.color.Gray_14, isIconBlack = false)

        scrollCenterWhenUserRankTouchDown()
    }

    private fun initViewModel() {
        viewModel.apply {
            fetchGameAndGroup(groupId)
        }
    }

    private fun setHomeRecyclerView() {
        setGameAdapter()
        setUserAdapter()
    }

    private fun setGameAdapter() {
        val onClick: (position: Int, gameId: Long) -> Unit = { position, gameId ->
            this.gameId = gameId
            // TODO : 넘어오는 값으로 groupId 변경 필요
            viewModel.setGameItemSelected(position)
            viewModel.fetchUserList(groupId, gameId)
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
                viewModel.fetchGameAndGroup(groupId)
            }
        }

        val copyButtonOnClick: (String) -> Unit = {
            binding.root.context.showToast(R.string.copy_access_code, Toast.LENGTH_SHORT)
            it.copyToClipboard(binding.root.context)
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

    private fun setCurrentGroupInfo(currentGroupInfo: DrawerGroupInfoUiModel.CurrentGroupInfo) {
        binding.apply {
            viewHeader.tvGroupName.text = currentGroupInfo.groupDetailItem.name
            tvGroupName.text = currentGroupInfo.groupDetailItem.name
            viewRankNotFound.tvCode.text = currentGroupInfo.groupDetailItem.accessCode
            viewRankNotFound.btnCodeCopy.setOnClickListener {
                currentGroupInfo.groupDetailItem.accessCode.copyToClipboard(root.context)
                binding.root.context.showToast(R.string.copy_access_code, Toast.LENGTH_SHORT)
            }
        }
    }

    private val userRankSnackBar: SnackBarHomeReload by lazy {
        SnackBarHomeReload.make(
            view = binding.root,
            onClick = {
                viewModel.fetchUserList(groupId, gameId)
            }
        )
    }

    private fun observeUserRankUiState(userRankAdapter: UserRankAdapter) {
        viewModel.userUiState.collectWithLifecycle(this) { uiState ->
            fun List<UserRankUiModel>.isSizeNoRankThreshold(): Boolean = this.size == HomeConfig.NO_RANK_THRESHOLD

            when (uiState) {
                is HomeUiState.Success -> {
                    userRankSnackBar.dismiss()
                    val isNoRank: Boolean = uiState.data.isSizeNoRankThreshold()

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
            onClick = {

                viewModel.fetchGameAndGroup(groupId)
            }
        )
    }

    private fun observeGameUiState(
        drawerGroupInfoAdapter: DrawerGroupInfoAdapter,
        userRankGameAdapter: UserRankGameAdapter,
    ) {
        viewModel.uiState.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is HomeUiState.Success -> {
                    uiState.data.group.map { uiModel ->
                        if (uiModel is DrawerGroupInfoUiModel.CurrentGroupInfo) {
                            setCurrentGroupInfo(uiModel)
                        }
                    }
                    drawerGroupInfoAdapter.submitList(uiState.data.group)
                    userRankGameAdapter.submitList(uiState.data.game)

                    binding.rvGameList.visibility = View.VISIBLE
                    gameSnackBar.dismiss()
                }

                is HomeUiState.Loading -> {
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

    private fun isSelectedPositionValid(): Boolean {
        return RV_SELECTED_POSITION_RESET != viewModel.getGameItemSelectedPosition()
    }
}
