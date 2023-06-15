package com.yapp.bol.presentation.view.match.game_select

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.FragmentGameSeleteBinding
import com.yapp.bol.presentation.model.GameItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSelectFragment : Fragment() {

    private var _binding: FragmentGameSeleteBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val gameSelectAdapter = GameSelectAdapter { gameName ->
        val bundle = Bundle().apply {
            putString(GAME_NAME, gameName)
        }
        findNavController().navigate(
            R.id.action_gameSelectFragment_to_memberSelectFragment, bundle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameSeleteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvProductItems.adapter = gameSelectAdapter

        gameSelectAdapter.submitList(testList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val GAME_NAME = "Game Name"
        val testList = List(10) {
            GameItem("$it", "테라포밍 마스")
        }
    }
}
