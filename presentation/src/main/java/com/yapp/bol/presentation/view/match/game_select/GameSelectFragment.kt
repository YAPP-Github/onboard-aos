package com.yapp.bol.presentation.view.match.game_select

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentGameSelectBinding
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GAME_SELECT
import com.yapp.bol.presentation.view.match.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSelectFragment : BaseFragment<FragmentGameSelectBinding>(R.layout.fragment_game_select) {

    private val matchViewModel: MatchViewModel by activityViewModels()
    private val gameSelectViewModel: GameSelectViewModel by viewModels()

    private val gameSelectAdapter = GameSelectAdapter { gameItem ->
        matchViewModel.updateGameName(gameItem.name)
        matchViewModel.updateGameId(gameItem.id)
        matchViewModel.updateGameImageUrl(gameItem.img)
        val bundle = Bundle().apply {
            putString(GAME_NAME, gameItem.name)
            putInt(MAX_PLAYER, gameItem.maxMember)
            putInt(MIN_PLAYER, gameItem.minMember)
        }
        findNavController().navigate(
            R.id.action_gameSelectFragment_to_memberSelectFragment, bundle
        )
    }

    override fun onViewCreatedAction() {
        binding.rvGameItems.adapter = gameSelectAdapter

        gameSelectViewModel.gameList.observe(viewLifecycleOwner) {
            gameSelectAdapter.submitList(it)
        }
        matchViewModel.updateToolBarTitle(requireContext().resources.getString(R.string.game_result_record))
        matchViewModel.updateCurrentPage(GAME_SELECT)

        gameSelectViewModel.updateGroupId(matchViewModel.groupId)
    }

    companion object {
        const val GAME_NAME = "Game Name"
        const val MAX_PLAYER = "max player"
        const val MIN_PLAYER = "min player"
    }
}
