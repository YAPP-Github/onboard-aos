package com.yapp.bol.presentation.view.match.member_select

import KeyboardVisibilityUtils
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.utils.KeyboardManager
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.dialog.GuestAddDialog
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberSelectFragment : Fragment() {

    private var _binding: FragmentMemberSelectBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val matchViewModel: MatchViewModel by activityViewModels()
    private val memberSelectViewModel: MemberSelectViewModel by viewModels()

    private val memberSelectAdapter = MemberSelectAdapter { member ->
        memberSelectViewModel.checkedSelectMembers(member)
        memberSelectViewModel.clearMembers(member.id, getInputTextValue())
    }
    private val membersAdapter = MembersAdapter { member, position, isChecked ->
        memberSelectViewModel.checkedSelectMembers(member)
        memberSelectViewModel.updateMemberIsChecked(position, isChecked)
    }

    private val keyboardManager by lazy {
        KeyboardManager(requireActivity())
    }

    private val guestAddDialog by lazy {
        GuestAddDialog(
            context = requireContext(),
            addGuest = { },
            getValidateNickName = { nickname -> memberSelectViewModel.getValidateNickName(10, nickname) },
        )
    }

    private val keyboardVisibilityUtils by lazy {
        KeyboardVisibilityUtils(
            window = activity?.window ?: throw Exception(),
            onHideKeyboard = { if (guestAddDialog.isShowing) guestAddDialog.dismiss() },
        )
    }

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
        matchViewModel.updateToolBarTitle(gameName)

        binding.rvMemberSelect.adapter = memberSelectAdapter
        binding.rvMembers.adapter = membersAdapter

        setViewModelObserve()
        setClickListener()

        binding.etSearchMember.doOnTextChanged { text, _, _, _ ->
            if ((text?.length ?: 0) > 0) binding.etSearchMember.requestFocus()
            memberSelectViewModel.updateSearchMembers(text.toString())
        }

        binding.etSearchMember.onFocusChangeListener = setFocusChangeListener()

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                keyboardManager.hideKeyboard()
                binding.etSearchMember.clearFocus()
            }
        }
        binding.rvMembers.addOnScrollListener(scrollListener)
        keyboardVisibilityUtils
    }

    private fun setViewModelObserve() {
        memberSelectViewModel.members.observe(viewLifecycleOwner) { members ->
            val isVisible = members.isEmpty()
            setSearchResultNothing(isVisible, getInputTextValue())
            membersAdapter.submitList(members)
        }

        memberSelectViewModel.isCompleteButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnPlayerComplete.isEnabled = it
        }

        memberSelectViewModel.players.observe(viewLifecycleOwner) { players ->
            memberSelectAdapter.submitList(players)
        }

        memberSelectViewModel.isNickNameValidate.observe(viewLifecycleOwner) {
            if (guestAddDialog.isShowing) guestAddDialog.setNicknameValid(it)
        }
    }

    private fun setSearchResultNothing(isVisible: Boolean, keyword: String) {
        val visible = if (isVisible) View.VISIBLE else View.GONE
        val searchResult = String.format(resources.getString(R.string.search_result_nothing), keyword)
        binding.viewSearchResultNothing.visibility = visible
        binding.tvSearchResultNothingGuide.visibility = visible
        binding.btnGuestAddNothing.visibility = visible
        binding.ivPlus.visibility = visible
        binding.tvSearchResultNothing.apply {
            text = searchResult
            visibility = visible
        }
    }

    private fun setClickListener() {
        binding.ivSearchIcon.setOnClickListener {
            if (binding.etSearchMember.isFocused) {
                binding.etSearchMember.text.clear()
                binding.etSearchMember.clearFocus()
            } else {
                keyboardManager.showKeyboard(binding.etSearchMember)
                binding.etSearchMember.requestFocus()
            }
        }
        binding.btnTempMember.setOnClickListener {
            keyboardManager.hideKeyboard()
            guestAddDialog.show()
        }
        binding.btnGuestAddNothing.setOnClickListener {
            keyboardManager.hideKeyboard()
            guestAddDialog.show()
        }

        binding.btnPlayerComplete.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelableArrayList(PLAYERS, memberSelectViewModel.dynamicPlayers)
            }
            findNavController().navigate(R.id.action_memberSelectFragment_to_gameResultFragment, bundle)
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

    override fun onDestroyView() {
        _binding = null
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    companion object {
        const val PLAYERS = "Players"
    }
}
