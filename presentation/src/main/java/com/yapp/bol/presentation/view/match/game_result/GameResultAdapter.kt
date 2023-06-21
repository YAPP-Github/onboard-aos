package com.yapp.bol.presentation.view.match.game_result

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvMemberMatchItemBinding
import com.yapp.bol.presentation.model.MemberResultItem

class GameResultAdapter(
    private val context: Context,
    private val gameResultUpdateListener: GameResultUpdateListener,
) : ListAdapter<MemberResultItem, GameResultAdapter.GameResultViewHolder>(diff) {

    interface GameResultUpdateListener {
        val updatePlayerScore: (Int, Int) -> Unit
        val changePlayerPosition: (Int, Int) -> Unit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvMemberMatchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding, context, gameResultUpdateListener)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()
    class GameResultViewHolder(
        private val binding: RvMemberMatchItemBinding,
        private val context: Context,
        private val gameResultUpdateListener: GameResultUpdateListener,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentAdapterPosition: Int = -1

        fun bind(item: MemberResultItem, position: Int) {
            currentAdapterPosition = position
            binding.tvMemberRank.text =
                String.format(context.resources.getString(R.string.game_result_rank), item.rank + 1)
            binding.tvMemberName.text = item.name
            val score = if (item.score == 0) "" else item.score.toString()
            binding.etGameScore.setText(score)

            binding.etGameScore.doOnTextChanged { text, _, _, _ ->
                val value = text.toString()
                val intValue = if (value.isEmpty()) 0 else value.toInt()
                gameResultUpdateListener.updatePlayerScore(position, intValue)
            }

            binding.root.setOnClickListener {
                val value = binding.etGameScore.text.toString()
                if (value.isEmpty()) return@setOnClickListener
                gameResultUpdateListener.changePlayerPosition(position, value.toInt())
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
    }
}
