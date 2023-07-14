package com.yapp.bol.presentation.view.match.game_select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGameSeleteBinding
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GAME_SELECT
import com.yapp.bol.presentation.view.match.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSelectFragment : Fragment() {

    private var _binding: FragmentGameSeleteBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val matchViewModel: MatchViewModel by activityViewModels()
    private val gameSelectViewModel: GameSelectViewModel by viewModels()

    private val gameSelectAdapter = GameSelectAdapter { gameName ->
        matchViewModel.updateGameName(gameName)
        val bundle = Bundle().apply {
            putString(GAME_NAME, gameName)
        }
        findNavController().navigate(
            R.id.action_gameSelectFragment_to_memberSelectFragment, bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGameSeleteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvGameItems.adapter = gameSelectAdapter

        gameSelectViewModel.gameList.observe(viewLifecycleOwner) {
            gameSelectAdapter.submitList(it)
        }
        matchViewModel.updateToolBarTitle(requireContext().resources.getString(R.string.game_result_record))
        matchViewModel.updateCurrentPage(GAME_SELECT)

        gameSelectViewModel.updateGroupId(matchViewModel.groupId)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val GAME_NAME = "Game Name"
    }
}
