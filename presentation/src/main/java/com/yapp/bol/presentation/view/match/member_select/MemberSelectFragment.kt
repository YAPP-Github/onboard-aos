package com.yapp.bol.presentation.view.match.member_select

import KeyboardVisibilityUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.KeyboardManager
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.MEMBER_SELECT
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.dialog.GuestAddDialog
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.MAX_PLAYER
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.MIN_PLAYER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberSelectFragment : Fragment() {

    private var _binding: FragmentMemberSelectBinding? = null
    private val binding get() = checkNotNull(_binding)

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
        setMaxPlayerText = ::setPlayerGuide
    )

    private val keyboardManager by lazy {
        KeyboardManager(requireActivity())
    }

    private val guestAddDialog by lazy {
        GuestAddDialog(
            context = requireContext(),
            addGuest = { nickname -> memberSelectViewModel.addGuestMember(nickname) },
            getValidateNickName = { nickname ->
                memberSelectViewModel.getValidateNickName(
                    matchViewModel.groupId,
                    nickname
                )
            },
        )
    }

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMemberSelectBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gameName = arguments?.getString(GAME_NAME) ?: EMPTY_STRING
        val maxPlayer = arguments?.getInt(MAX_PLAYER) ?: 0
        val minPlayer = arguments?.getInt(MIN_PLAYER) ?: 0

        matchViewModel.updateToolBarTitle(gameName)
        matchViewModel.updateCurrentPage(MEMBER_SELECT)
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
        setClickListener()

        binding.etSearchMember.doOnTextChanged { text, _, _, _ ->
            if ((text?.length ?: 0) > 0) binding.etSearchMember.requestFocus()
            memberSelectViewModel.clearNextPage()
            memberSelectViewModel.getMembers(getInputTextValue())
        }

        binding.etSearchMember.onFocusChangeListener = setFocusChangeListener()

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                getNextMember(recyclerView)
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

        userId.observe(viewLifecycleOwner) {
            if(it == -1) return@observe
            memberSelectViewModel.addMember(it)
        }
    }

    private fun setSearchResultNothing(visible: Boolean, keyword: String) = with(binding) {
        val searchResult = String.format(resources.getString(R.string.search_result_nothing), keyword)
        viewSearchResultNothing.isVisible = visible
        tvSearchResultNothingGuide.isVisible = visible
        btnGuestAddNothing.isVisible = visible
        ivPlus.isVisible = visible
        tvSearchResultNothing.apply {
            text = searchResult
            isVisible = visible
        }
    }

    private fun setClickListener() = with(binding) {
        ivSearchIcon.setOnClickListener {
            if (etSearchMember.isFocused) {
                etSearchMember.text.clear()
                etSearchMember.clearFocus()
            } else {
                keyboardManager.showKeyboard(etSearchMember)
                etSearchMember.requestFocus()
            }
        }
        btnTempMember.setOnClickListener {
            keyboardManager.hideKeyboard()
            guestAddDialog.show()
        }
        btnGuestAddNothing.setOnClickListener {
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
                if (hasFocus) R.drawable.ic_cancel_gray else R.drawable.ic_search,
            )
            binding.ivSearchIcon.setImageDrawable(image)
        }
    }

    private fun getInputTextValue(): String {
        return binding.etSearchMember.text.toString()
    }

    private fun setPlayerGuide() {
        val text: String
        val color: Int
        val isMaxPlayers = memberSelectViewModel.checkedMaxPlayers()
        if (isMaxPlayers) {
            text = getString(R.string.play_select_guide)
            color = R.color.Gray_10
        } else {
            text = String.format(
                requireContext().resources.getString(R.string.max_player_guide),
                memberSelectViewModel.players.value?.size
            )
            color = R.color.Orange_10
        }
        binding.tvMemberSelectGuide.text = text
        binding.tvMemberSelectGuide.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    override fun onDestroyView() {
        _binding = null
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    companion object {
        const val PLAYERS = "Players"
    }
}
