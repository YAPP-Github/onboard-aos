package com.yapp.bol.presentation.view.match.member_select

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.yapp.bol.presentation.databinding.FragmentMemberSelectBinding
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.game_select.GameSelectFragment.Companion.GAME_NAME
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberSelectFragment : Fragment() {

    private var _binding: FragmentMemberSelectBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: MatchViewModel by activityViewModels()

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
