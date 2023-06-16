package com.yapp.bol.presentation.view.match.member_select

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.utils.GuestAddDialog
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberSelectFragment : Fragment() {

    private var _binding: FragmentMemberSelectBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val matchViewModel: MatchViewModel by activityViewModels()

    private val memberSelectAdapter = MemberSelectAdapter { member ->
        matchViewModel.checkedSelectMembers(member)
        matchViewModel.clearMembers(member.id, getInputTextValue())
    }
    private val membersAdapter = MembersAdapter { member, isChecked ->
        matchViewModel.checkedSelectMembers(member)
        matchViewModel.updateMemberIsChecked(member.id, isChecked)
    }

    private val inputManager by lazy {
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemberSelectBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gameName = arguments?.getString(GAME_NAME) ?: ""
        matchViewModel.updateToolBarTitle(gameName)

        binding.rvMemberSelect.adapter = memberSelectAdapter
        binding.rvMembers.adapter = membersAdapter

        setViewModelObserve()
        setClickListener()

        binding.etSearchMember.doOnTextChanged { text, _, _, _ ->
            matchViewModel.updateSearchMembers(text.toString())
        }

        binding.etSearchMember.onFocusChangeListener = setFocusChangeListener()

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard()
                binding.etSearchMember.clearFocus()
            }
        }
        binding.rvMembers.addOnScrollListener(scrollListener)
    }

    private fun setViewModelObserve() {
        matchViewModel.members.observe(viewLifecycleOwner) { members ->
            membersAdapter.submitList(members)
        }

        matchViewModel.isCompleteButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnPlayerComplete.isEnabled = it
        }

        matchViewModel.players.observe(viewLifecycleOwner) { players ->
            memberSelectAdapter.submitList(players)
        }
    }

    private fun setClickListener() {
        binding.ivSearchIcon.setOnClickListener {
            if (binding.etSearchMember.isFocused) {
                hideKeyboard()
                binding.etSearchMember.text.clear()
                binding.etSearchMember.clearFocus()
            } else {
                showKeyboard(binding.etSearchMember)
                binding.etSearchMember.requestFocus()
            }
        }

        binding.btnTempMember.setOnClickListener {
            generateGuestAddDialog { name -> }
        }
    }

    private fun setFocusChangeListener(): View.OnFocusChangeListener {
        return View.OnFocusChangeListener { _, hasFocus ->
            val image = ContextCompat.getDrawable(
                requireContext(),
                if (hasFocus) R.drawable.ic_cancel_gray else R.drawable.ic_search
            )
            binding.ivSearchIcon.setImageDrawable(image)
        }
    }

    private fun generateGuestAddDialog(checkedGalleryAccess: (String) -> Unit) {
        val dialog = GuestAddDialog(requireContext(), checkedGalleryAccess)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        dialog.show()
    }

    private fun getInputTextValue(): String {
        return binding.etSearchMember.text.toString()
    }

    private fun hideKeyboard() {
        if (activity == null && activity?.currentFocus == null) return
        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun showKeyboard(editText: EditText) {
        if (activity == null && activity?.currentFocus == null) return
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
