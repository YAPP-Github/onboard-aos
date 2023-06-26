package com.yapp.bol.presentation.view.match.game_result

import android.content.Context
import android.graphics.Color
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvPlayerMatchItemBinding
import com.yapp.bol.presentation.model.MemberResultItem
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING

class GameResultAdapter(
    private val context: Context,
    private val gameResultUpdateListener: GameResultUpdateListener,
) : ListAdapter<MemberResultItem, GameResultAdapter.GameResultViewHolder>(diff) {

    interface GameResultUpdateListener {
        fun updatePlayerScore(position: Int, value: Int?)
        fun updatePlayers()
        fun showKeyboard(editText: EditText)
        fun moveScroll(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvPlayerMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding, context, gameResultUpdateListener)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()
    class GameResultViewHolder(
        private val binding: RvPlayerMatchItemBinding,
        private val context: Context,
        private val gameResultUpdateListener: GameResultUpdateListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberResultItem, position: Int) {
            setTextView(item)
            setClickListener(position)
            setTextChangeListener(position)
        }

        private fun setTextView(item: MemberResultItem) {
            binding.tvMemberRank.text =
                String.format(context.resources.getString(R.string.game_result_rank), item.rank + 1)
            val textColor = if (item.rank == 0) Color.parseColor("#FF4D0D") else Color.GRAY
            binding.tvMemberRank.setTextColor(textColor)
            binding.tvMemberName.text = item.nickname
            val score = if (item.score == null) EMPTY_STRING else item.score.toString()
            binding.etGameScore.setText(score)
        }

        private fun setClickListener(position: Int) {
            binding.etGameScore.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    val value = binding.etGameScore.text.toString()
                    if (value.isNotEmpty()) gameResultUpdateListener.updatePlayers()
                    gameResultUpdateListener.moveScroll(position + MOVE_SCROLL_POINT)
                }
                false
            }

            binding.root.setOnClickListener {
                binding.etGameScore.requestFocus()
                binding.etGameScore.setSelection(binding.etGameScore.text.length)
                gameResultUpdateListener.showKeyboard(binding.etGameScore)
            }
        }

        private fun setTextChangeListener(position: Int) {
            binding.etGameScore.doAfterTextChanged { text ->
                val value = text.toString()
                val intValue = if (value.isEmpty()) null else value.toInt()
                gameResultUpdateListener.updatePlayerScore(position, intValue)
            }
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<MemberResultItem>() {
            override fun areItemsTheSame(oldItem: MemberResultItem, newItem: MemberResultItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemberResultItem, newItem: MemberResultItem): Boolean {
                return oldItem == newItem
            }
        }

        const val MOVE_SCROLL_POINT = 2
    }
}
