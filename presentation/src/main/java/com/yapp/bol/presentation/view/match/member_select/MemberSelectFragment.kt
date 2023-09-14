package com.yapp.bol.presentation.view.match.member_select

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.KeyboardManager
import com.yapp.bol.presentation.utils.KeyboardVisibilityUtils
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.utils.createScaleHeightAnimator
import com.yapp.bol.presentation.utils.dpToPx
import com.yapp.bol.presentation.utils.textChangesToFlow
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.MEMBER_SELECT
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.dialog.GuestAddDialog
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.MAX_PLAYER
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.MIN_PLAYER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.yapp.bol.designsystem.R as DR

@AndroidEntryPoint
class MemberSelectFragment : BaseFragment<FragmentMemberSelectBinding>(R.layout.fragment_member_select) {

    private val matchViewModel: MatchViewModel by activityViewModels()
    private val memberSelectViewModel: MemberSelectViewModel by viewModels()

    private val memberSelectAdapter = MemberSelectAdapter(
        memberDeleteClickListener = { member ->
            memberSelectViewModel.checkedSelectMembers(member)
            memberSelectViewModel.removeMember(member.id)
        },
    )
    private val membersAdapter = MembersAdapter(
        memberClickListener = { member, position, isChecked ->
            memberSelectViewModel.checkedSelectMembers(member)
            memberSelectViewModel.updateMemberIsChecked(position, isChecked)
        },
        checkedMaxPlayer = { memberSelectViewModel.checkedMaxPlayers() },
    )

    private val keyboardManager by lazy {
        KeyboardManager(requireActivity())
    }

    private val guestAddDialog by lazy {
        GuestAddDialog(
            context = requireContext(),
            addGuest = { nickname -> memberSelectViewModel.addGuestMember(nickname) },
            scope = viewLifecycleOwner.lifecycleScope,
            getValidateNickName = { nickname ->
                memberSelectViewModel.getValidateNickName(
                    matchViewModel.groupId,
                    nickname
                )
            },
        )
    }

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onViewCreatedAction() {
        val gameName = arguments?.getString(GAME_NAME) ?: EMPTY_STRING
        val maxPlayer = arguments?.getInt(MAX_PLAYER) ?: 0
        val minPlayer = arguments?.getInt(MIN_PLAYER) ?: 0

        matchViewModel.updateToolBarTitle(gameName)
        matchViewModel.updatePageState(MEMBER_SELECT)
        memberSelectViewModel.updateGroupId(matchViewModel.groupId)
        memberSelectViewModel.setMaxPlayers(maxPlayer)
        memberSelectViewModel.setMinPlayers(minPlayer)

        binding.rvMemberSelect.adapter = memberSelectAdapter
        binding.rvMembers.adapter = membersAdapter

        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window = activity?.window ?: throw Exception(),
            onHideKeyboard = { if (guestAddDialog.isShowing) guestAddDialog.dismiss() },
        )

        setViewModelObserve()
        setPlayerRangeText(minPlayer, maxPlayer)
        setClickListener()

        viewLifecycleOwner.lifecycleScope.launch {
            val editTextFlow = binding.etSearchMember.textChangesToFlow()
            val debounceDuration = 300L

            editTextFlow.debounce(debounceDuration)
                .onEach {
                    val keyword = it.toString()

                    if (keyword.isNotEmpty()) binding.etSearchMember.requestFocus()
                    memberSelectViewModel.clearNextPage()
                    memberSelectViewModel.getMembers(getInputTextValue())
                }
                .launchIn(this)
        }

        binding.etSearchMember.onFocusChangeListener = setFocusChangeListener()

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                getNextMember(recyclerView)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                keyboardManager.hideKeyboard()
            }
        }
        binding.rvMembers.addOnScrollListener(scrollListener)
    }

    private fun getNextMember(recyclerView: RecyclerView) {
        val newPagePointItemVisible =
            (recyclerView.layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition() ?: 0

        val itemTotalCount = membersAdapter.itemCount - 10

        if (newPagePointItemVisible == itemTotalCount) {
            memberSelectViewModel.getMembers()
        }
    }

    private fun setViewModelObserve() = with(memberSelectViewModel) {
        members.observe(viewLifecycleOwner) { members ->
            val isVisible = members.isEmpty()
            setSearchResultNothing(isVisible, getInputTextValue())
            membersAdapter.submitList(members)
        }

        isCompleteButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnPlayerComplete.isEnabled = it
        }

        players.observe(viewLifecycleOwner) { players ->
            memberSelectAdapter.submitList(players)
        }

        isNickNameValidate.observe(viewLifecycleOwner) {
            if (guestAddDialog.isShowing) guestAddDialog.setNicknameValid(it)
        }

        playerState.collectWithLifecycle(viewLifecycleOwner) {
            if (it == null) return@collectWithLifecycle
            binding.rvMemberSelect.animateViewHeight(0, requireContext().dpToPx(96), it)
        }
    }

    private fun setSearchResultNothing(visible: Boolean, keyword: String) = with(binding) {
        val searchResult = String.format(resources.getString(R.string.search_result_nothing), keyword)
        viewSearchResultNothing.isVisible = visible
        tvSearchResultNothingGuide.isVisible = visible
        btnGuestAddNothing.isVisible = visible
        ivPlusNothing.isVisible = visible
        tvSearchResultNothing.apply {
            text = searchResult
            isVisible = visible
        }
    }

    private fun setPlayerRangeText(minPlayer: Int, maxPlayer: Int) {
        val playerRangeText = if (minPlayer == maxPlayer) {
            getString(R.string.game_player_count_guide_2, minPlayer)
        } else {
            getString(R.string.game_player_count_guide_1, minPlayer, maxPlayer)
        }
        binding.tvPlayerRange.text = playerRangeText
    }

    private fun setClickListener() = with(binding) {
        ivSearchIcon.setOnClickListener {
            if (etSearchMember.isFocused) {
                etSearchMember.text.clear()
                etSearchMember.clearFocus()
                keyboardManager.hideKeyboard()
            } else {
                etSearchMember.requestFocus()
                keyboardManager.showKeyboard(etSearchMember)
            }
        }

        btnGuestAddNothing.setOnClickListener {
            keyboardManager.hideKeyboard()
            guestAddDialog.show()
        }

        btnGuestAdd.setOnClickListener {
            keyboardManager.hideKeyboard()
            guestAddDialog.show()
        }

        btnPlayerComplete.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelableArrayList(PLAYERS, memberSelectViewModel.dynamicPlayers)
            }
            findNavController().navigate(
                R.id.action_memberSelectFragment_to_gameResultFragment,
                bundle
            )
        }
    }

    private fun setFocusChangeListener(): View.OnFocusChangeListener {
        return View.OnFocusChangeListener { _, hasFocus ->
            val image = ContextCompat.getDrawable(
                requireContext(),
                if (hasFocus) DR.drawable.ic_cancel_gray else DR.drawable.ic_search_gray,
            )
            binding.ivSearchIcon.setImageDrawable(image)
        }
    }

    private fun getInputTextValue(): String {
        return binding.etSearchMember.text.toString()
    }

    private fun View.animateViewHeight(startHeight: Int, endHeight: Int, isScaleIn: Boolean) {
        if (isScaleIn) {
            this.createScaleHeightAnimator(200L, startHeight, endHeight)
        } else {
            this.createScaleHeightAnimator(200L, endHeight, startHeight)
        }
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    companion object {
        const val PLAYERS = "Players"
    }
}
