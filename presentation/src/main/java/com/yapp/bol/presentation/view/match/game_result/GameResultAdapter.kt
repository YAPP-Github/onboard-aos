package com.yapp.bol.presentation.view.match.game_result

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvPlayerMatchItemBinding
import com.yapp.bol.presentation.model.MemberResultItem
import com.yapp.bol.presentation.utils.Constant.EMPTY_STRING
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST
import com.yapp.bol.designsystem.R as DR

class GameResultAdapter(
    private val context: Context,
    private val gameResultUpdateListener: GameResultUpdateListener,
) : ListAdapter<MemberResultItem, GameResultAdapter.GameResultViewHolder>(diff) {

    interface GameResultUpdateListener {
        fun updatePlayerScore(position: Int, value: Int?)
        fun updatePlayers()
        fun showKeyboard(editText: EditText)
        fun moveScroll(position: Int)
        fun updateFocusState(position: Int)
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
            setImageView(item)
            val color = if (item.score == null) DR.color.Gray_6 else DR.color.Gray_11
            binding.itemLine.setBackgroundColor(ContextCompat.getColor(context, color))
        }

        private fun setImageView(item: MemberResultItem) {
            val image = if (item.role == GUEST) R.drawable.img_dice_empty_large else DR.drawable.img_dice
            binding.ivMemberLevelIcon.setImageResource(image)
        }

        private fun setTextView(item: MemberResultItem) {
            binding.tvMemberRank.text =
                String.format(context.resources.getString(R.string.game_result_rank), item.rank + 1)
            val textColor = if (item.rank == 0) {
                ContextCompat.getColor(context, DR.color.Orange_10)
            } else {
                ContextCompat.getColor(context, DR.color.Gray_9)
            }
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
                gameResultUpdateListener.updateFocusState(position)
            }
        }

        private fun setTextChangeListener(position: Int) {
            binding.etGameScore.doAfterTextChanged { text ->
                val value = text.toString()
                val color: Int
                val intValue = if (value.isEmpty()) {
                    color = ContextCompat.getColor(context, DR.color.Gray_6)
                    null
                } else {
                    color = ContextCompat.getColor(context, DR.color.Gray_11)
                    value.toInt()
                }
                binding.itemLine.setBackgroundColor(color)
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
