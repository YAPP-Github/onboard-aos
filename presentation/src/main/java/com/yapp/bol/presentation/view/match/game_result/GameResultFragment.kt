package com.yapp.bol.presentation.view.match.game_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yapp.bol.presentation.databinding.FragmentGameResultBinding
import com.yapp.bol.presentation.model.MemberItem
import com.yapp.bol.presentation.view.match.MatchViewModel

class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val gameResultViewModel: GameResultViewModel by viewModels()

    private val members = MutableList(10) { MemberItem(it, "$it. Test", 1) }

    private val gameResultAdapter by lazy {
        GameResultAdapter(
            list = members,
            getTargetPosition = gameResultViewModel::getTargetPosition,
            updateValue = gameResultViewModel::updatePlayerValue,
        )
    }
    private val matchViewModel: MatchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameResultBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvMembers.adapter = gameResultAdapter
        binding.rvMembersRank.adapter = GameResultRankAdapter(context = context?: throw Exception(), members.size)
        matchViewModel.updateToolBarTitle("결과 기록")

    }
}
