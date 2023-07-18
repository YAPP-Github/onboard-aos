package com.yapp.bol.presentation.view.home.rank

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentHomeRankBinding
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.config.HomeConfig
import com.yapp.bol.presentation.utils.copyToClipboard
import com.yapp.bol.presentation.utils.setNavigationBarColor
import com.yapp.bol.presentation.utils.setStatusBarColor
import com.yapp.bol.presentation.utils.showToast
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

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()

        initViewModel()

        setHomeRecyclerView()
        setDrawer()

        observeUserRankUiState()
        observeGameUiState()

        setStatusBarColor(this@HomeRankFragment.requireActivity(), designsystemR.color.Gray_14, isIconBlack = false)
        setNavigationBarColor(this@HomeRankFragment.requireActivity(), designsystemR.color.Gray_14)

        scrollCenterWhenUserRankTouchDown()
    }

    private fun initViewModel() {
        viewModel.apply {
            fetchGameList(groupId)
            fetchJoinedGroupList(groupId)
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
        val userRankGameAdapter = UserRankGameAdapter(onClick, scrollAnimation)

        viewModel.gameListFlow.collectWithLifecycle(this) { gameList ->
            userRankGameAdapter.submitList(gameList)
        }

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

        viewModel.userListFlow.collectWithLifecycle(this) { userList ->
            if (userList.size == HomeConfig.NO_RANK_THRESHOLD) {
                binding.viewRankNotFound.root.visibility = View.VISIBLE
                binding.rvUserRank.visibility = View.GONE
            } else {
                binding.viewRankNotFound.root.visibility = View.GONE
                binding.rvUserRank.visibility = View.VISIBLE
                userRankAdapter.submitList(userList)
            }
        }
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
                fetchGameList(groupId = id)
                fetchJoinedGroupList(id)
            }
        }

        val copyButtonOnClick: (String) -> Unit = {
            binding.root.context.showToast(R.string.copy_access_code, Toast.LENGTH_SHORT)
            it.copyToClipboard(binding.root.context)
        }

        val drawerGroupInfoAdapter = DrawerGroupInfoAdapter(
            otherGroupOnClick = otherGroupOnClick,
            copyButtonOnClick = copyButtonOnClick,
        )
        binding.rvGroupInfo.adapter = drawerGroupInfoAdapter

        viewModel.groupListFlow.collectWithLifecycle(this) { uiModelList ->
            drawerGroupInfoAdapter.submitList(uiModelList)
            uiModelList.map { uiModel ->
                if (uiModel is DrawerGroupInfoUiModel.CurrentGroupInfo) {
                    setCurrentGroupInfo(uiModel)
                }
            }
        }
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

    private fun observeUserRankUiState() {
        viewModel.userUiState.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is HomeUiState.Success -> {
                    if (RV_SELECTED_POSITION_RESET != viewModel.getGameItemSelectedPosition()) {
                        binding.rvGameList.smoothScrollToPosition(viewModel.getGameItemSelectedPosition())
                    }
                    binding.viewRankLoading.visibility = View.GONE
                    userRankSnackBar.dismiss()
                }

                is HomeUiState.Loading -> {
                    binding.rvUserRank.visibility = View.GONE
                    binding.viewRankNotFound.root.visibility = View.GONE
                    binding.viewRankLoading.visibility = View.VISIBLE
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
                viewModel.fetchGameList(groupId)
                viewModel.fetchJoinedGroupList(groupId)
            }
        )
    }

    private fun observeGameUiState() {
        viewModel.groupUiState.collectWithLifecycle(this) { uiState ->
            when (uiState) {
                is HomeUiState.Success -> {
                    binding.viewGameLoading.visibility = View.GONE
                    binding.rvGameList.visibility = View.VISIBLE
                    gameSnackBar.dismiss()
                }

                is HomeUiState.Loading -> {
                    binding.viewGameLoading.visibility = View.VISIBLE
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
        binding.rvUserRank.setOnTouchListener { view, motionEvent ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                if (RV_SELECTED_POSITION_RESET != viewModel.getGameItemSelectedPosition()) {
                    binding.rvGameList.smoothScrollToPosition(viewModel.getGameItemSelectedPosition())
                }
            }
            true
        }
    }
}
