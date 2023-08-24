package com.yapp.bol.presentation.view.match.dialog.result_record

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvPlayerRecordItemBinding
import com.yapp.bol.presentation.model.MemberResultItem
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST
import com.yapp.bol.designsystem.R as DR

class ResultRecordAdapter(
    private val context: Context
) : ListAdapter<MemberResultItem, ResultRecordAdapter.GameResultViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvPlayerRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GameResultViewHolder(
        private val context: Context,
        private val binding: RvPlayerRecordItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberResultItem) {
            setImageView(item)
            setTextView(item)
        }

        private fun setImageView(item: MemberResultItem) {
            val image = if (item.role == GUEST) DR.drawable.img_dice_empty_small else DR.drawable.img_dice
            binding.ivMemberLevelIcon.setImageResource(image)
        }

        private fun setTextView(item: MemberResultItem) {
            val rank = item.rank + 1
            binding.tvMemberRank.text = rank.toString()
            binding.tvMemberName.text = item.nickname
            binding.tvMemberScore.text = item.score.toString()

            val color = if (item.rank == 0) R.color.Orange_10 else R.color.Gray_15
            val textColor = ContextCompat.getColor(context, color)

            binding.tvMemberRank.setTextColor(textColor)
            binding.tvMemberScore.setTextColor(textColor)
            binding.tvMemberName.setTextColor(textColor)
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
