package com.yapp.bol.presentation.view.match.game_result

import KeyboardVisibilityUtils
import android.view.View
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.base.BaseFragment
import com.yapp.bol.presentation.databinding.FragmentGameResultBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.model.ResultRecordItem
import com.yapp.bol.presentation.utils.KeyboardManager
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GAME_RESULT
import com.yapp.bol.presentation.view.match.MatchViewModel
import com.yapp.bol.presentation.view.match.dialog.result_record.ResultRecordDialog
import com.yapp.bol.presentation.view.match.member_select.MemberSelectFragment.Companion.PLAYERS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class GameResultFragment : BaseFragment<FragmentGameResultBinding>(R.layout.fragment_game_result) {

    private val gameResultViewModel: GameResultViewModel by viewModels()
    private val matchViewModel: MatchViewModel by activityViewModels()

    private val currentTime = getCurrentTime()

    private val keyboardManager by lazy {
        KeyboardManager(requireActivity())
    }

    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    private val resultRecordDialog
        get() = ResultRecordDialog(
            requireContext(),
            ResultRecordItem(
                gameName = matchViewModel.gameName,
                player = gameResultViewModel.players.value ?: listOf(),
                currentTime = List(2) { currentTime.split(DATE_DELIMITERS)[it] },
                resultRecording = ::resultRecording
            )
        ) {
            gameResultViewModel.postMatch(
                matchViewModel.gameId.toInt(),
                matchViewModel.groupId,
                matchViewModel.currentTime
            )
        }

    private val gameResultAdapter by lazy {
        GameResultAdapter(
            context = requireContext(),
            gameResultUpdateListener = object : GameResultAdapter.GameResultUpdateListener {
                override fun updatePlayerScore(position: Int, value: Int?) {
                    gameResultViewModel.updatePlayerScore(position, value)
                }

                override fun updatePlayers() {
                    gameResultViewModel.updatePlayers()
                }

                override fun showKeyboard(editText: EditText) {
                    keyboardManager.showKeyboard(editText)
                }

                override fun moveScroll(position: Int) {
                    moveScrollNextPosition(position)
                }
            }
        )
    }

    override fun onViewCreatedAction() {
        val players = arguments?.getParcelableArrayList<MemberInfo>(PLAYERS)
        gameResultViewModel.initPlayers(players ?: arrayListOf())
        binding.rvPlayers.adapter = gameResultAdapter
        setTextView()
        setViewModelObserve()
        setClickListener()
        setScrollListener()

        matchViewModel.updateToolBarTitle(GAME_RESULT_TITLE)
        matchViewModel.updateCurrentPage(GAME_RESULT)
        matchViewModel.updateCurrentTime(currentTime)
        keyboardVisibilityUtils = KeyboardVisibilityUtils(
            window = activity?.window ?: throw Exception(),
            onHideKeyboard = gameResultViewModel::updatePlayers,
        )
    }

    private fun setViewModelObserve() {
        gameResultViewModel.players.observe(viewLifecycleOwner) {
            gameResultAdapter.submitList(it)
        }

        gameResultViewModel.recordCompleteIsEnabled.observe(viewLifecycleOwner) {
            binding.btnRecordComplete.isEnabled = it
        }
    }

    private fun setTextView() {
        binding.tvGameRecordGuide.text =
            String.format(requireContext().resources.getString(R.string.game_record_guide), matchViewModel.gameName)
        val temp = currentTime.split(DATE_DELIMITERS)
        binding.tvCalendar.text = temp[0]
        binding.tvClock.text = temp[1]
    }

    private fun setClickListener() {
        binding.btnRecordComplete.setOnClickListener {
            resultRecordDialog.show()
        }
    }

    private fun setScrollListener() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                keyboardManager.hideKeyboard()
            }
        }
        binding.rvPlayers.addOnScrollListener(scrollListener)
    }

    private fun getCurrentTime(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val stringFormat = SimpleDateFormat(DATE_FORMAT, Locale.KOREA)
        return stringFormat.format(date)
    }

    private fun moveScrollNextPosition(position: Int) {
        binding.rvPlayers.scrollToPosition(position)
    }

    private fun resultRecording() {
        lifecycleScope.launch {
            generateProgressBar()
            delay(1000)
            completeRecord()
        }
    }

    private fun generateProgressBar() {
        binding.loadingBackground.visibility = View.VISIBLE
        binding.tvLoadingText.visibility = View.VISIBLE
        binding.pbLoading.visibility = View.VISIBLE
    }

    private fun completeRecord() {
        binding.tvGameRecordComplete.text =
            String.format(
                requireContext().resources.getString(R.string.game_result_recording_complete),
                matchViewModel.gameName,
            )
        binding.tvLoadingText.visibility = View.GONE
        binding.pbLoading.visibility = View.GONE
        binding.tvGameRecordComplete.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    companion object {
        const val GAME_RESULT_TITLE = "결과 기록"
        const val DATE_FORMAT = "yy/MM/dd hh:mm"
        const val DATE_DELIMITERS = " "
    }
}
