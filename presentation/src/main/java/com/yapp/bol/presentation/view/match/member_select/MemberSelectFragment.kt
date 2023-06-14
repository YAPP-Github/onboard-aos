package com.yapp.bol.presentation.view.match.member_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
        viewModel.removeSelectMembers(member)
    }
    private val membersAdapter by lazy {
        MembersAdapter(requireContext()) { member, isChecked ->
            if (isChecked) viewModel.addSelectMembers(member)
            else viewModel.removeSelectMembers(member)
        }
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

        viewModel.getMembers()
        viewModel.selectMembers.filterNotNull().collectWithLifecycle(this) {
            memberSelectAdapter.submitList(it)
        }

        memberSelectAdapter.setCallback(membersAdapter)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
