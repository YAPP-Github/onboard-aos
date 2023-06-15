package com.yapp.bol.presentation.view.match.member_select

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.utils.collectWithLifecycle
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull

@AndroidEntryPoint
class MemberSelectFragment : Fragment() {

    private var _binding: FragmentMemberSelectBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: MatchViewModel by activityViewModels()

    private val memberSelectAdapter = MemberSelectAdapter { member ->
        viewModel.checkedSelectMembers(member)
        viewModel.clearMembers(member.id, binding.etSearchMember.text.toString())
    }
    private val membersAdapter = MembersAdapter { member, value ->
        viewModel.checkedSelectMembers(member)
        viewModel.updateMembersIsChecked(member.id, value)
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
        viewModel.updateToolBarTitle(gameName)

        binding.rvMemberSelect.adapter = memberSelectAdapter
        binding.rvMembers.adapter = membersAdapter

        viewModel.members.observe(viewLifecycleOwner) { members ->
            membersAdapter.submitList(members)
        }

        viewModel.players.filterNotNull().collectWithLifecycle(this) {
            memberSelectAdapter.submitList(it)
        }

        viewModel.isCompleteButtonEnabled.observe(viewLifecycleOwner) {
            binding.btnPlayerComplete.isEnabled = it
        }

        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard()
                binding.etSearchMember.clearFocus()
            }
        }
        binding.rvMembers.addOnScrollListener(scrollListener)

        binding.etSearchMember.doOnTextChanged { text, start, before, count ->
            viewModel.clearMembers(search = text.toString())
        }

        val focus = View.OnFocusChangeListener { v, hasFocus ->
            val image = ContextCompat.getDrawable(
                requireContext(),
                if (hasFocus) R.drawable.ic_cancel_gray else R.drawable.ic_search
            )
            binding.ivSearchIcon.setImageDrawable(image)
        }
        binding.etSearchMember.onFocusChangeListener = focus

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
    }

    private fun hideKeyboard() {
        if (activity == null && activity?.currentFocus == null) return
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun showKeyboard(editText: EditText) {
        if (activity == null && activity?.currentFocus == null) return
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
