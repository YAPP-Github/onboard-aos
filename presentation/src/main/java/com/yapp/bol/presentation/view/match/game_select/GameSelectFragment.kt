package com.yapp.bol.presentation.view.match.game_select

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentGameSeleteBinding
import com.yapp.bol.presentation.firebase.analysis.GA
import com.yapp.bol.presentation.view.match.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSelectFragment : BaseFragment<FragmentGameSeleteBinding>(R.layout.fragment_game_selete) {

    private val matchViewModel: MatchViewModel by activityViewModels()
    private val gameSelectViewModel: GameSelectViewModel by viewModels()

    private val gameSelectAdapter = GameSelectAdapter { gameName ->
        matchViewModel.updateGameName(gameName)
        val bundle = Bundle().apply {
            putString(GAME_NAME, gameName)
        }
        findNavController().navigate(
            R.id.action_gameSelectFragment_to_memberSelectFragment,
            bundle,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvGameItems.adapter = gameSelectAdapter

        gameSelectViewModel.gameList.observe(viewLifecycleOwner) {
            gameSelectAdapter.submitList(it)
        }
        matchViewModel.updateToolBarTitle(requireContext().resources.getString(R.string.game_result_record))

        gameSelectViewModel.updateGroupId(matchViewModel.groupId)
    }

    override fun getScreenName(): String = GA.Screen.GAME_SELECT

    companion object {
        const val GAME_NAME = "Game Name"
    }
}
