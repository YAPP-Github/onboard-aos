package com.yapp.bol.presentation.view.match.game_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yapp.bol.presentation.databinding.FragmentGameResultBinding
import com.yapp.bol.presentation.view.match.MatchViewModel

class GameResultFragment : Fragment() {

    private var _binding: FragmentGameResultBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val gameResultViewModel: GameResultViewModel by viewModels()

    private val gameResultAdapter by lazy {
        GameResultAdapter(
            context = requireContext(),
            gameResultUpdateListener = object : GameResultAdapter.GameResultUpdateListener {
                override val updatePlayerScore: (Int, Int) -> Unit
                    get() = gameResultViewModel::updatePlayerScore
                override val changePlayerPosition: (Int, Int) -> Unit
                    get() = gameResultViewModel::changePlayerPosition
            }
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

        gameResultViewModel.players.observe(viewLifecycleOwner) {
            gameResultAdapter.submitList(it)
        }

        gameResultViewModel.recordCompleteIsEnabled.observe(viewLifecycleOwner) {
            binding.btnRecordComplete.isEnabled = it
        }

        matchViewModel.updateToolBarTitle(GAME_RESULT_TITLE)
    }

    companion object {
        const val GAME_RESULT_TITLE = "결과 기록"
    }
}
