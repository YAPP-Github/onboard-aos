package com.yapp.bol.presentation.view.match.dialog.result_record

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.GameResultRecordDialogBinding
import com.yapp.bol.presentation.model.ResultRecordItem
import com.yapp.bol.presentation.utils.dialogWidthResize

class ResultRecordDialog(
    private val context: Context,
    private val resultRecordItem: ResultRecordItem,
    private val postMatch: () -> Unit,
) : Dialog(context) {

    private lateinit var binding: GameResultRecordDialogBinding

    private val resultRecordAdapter by lazy {
        ResultRecordAdapter(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = GameResultRecordDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        context.dialogWidthResize(this, 0.9f, true)
        binding.rvPlayers.adapter = resultRecordAdapter
        resultRecordAdapter.submitList(resultRecordItem.player)

        setTextView()
        setClickListener()
    }

    private fun setTextView() {
        binding.tvGameName.text = resultRecordItem.gameName
        binding.tvCalendar.text = resultRecordItem.currentTime[0]
        binding.tvClock.text = resultRecordItem.currentTime[1]
        binding.tvPlayerCount.text =
            String.format(context.resources.getString(R.string.game_player_count), resultRecordItem.player.size)
    }

    private fun setClickListener() {
        binding.btnProfileComplete.setOnClickListener {
            dismiss()
            postMatch()
            resultRecordItem.resultRecording()
        }
        binding.ibCancel.setOnClickListener {
            dismiss()
        }
    }
}
