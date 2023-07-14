package com.yapp.bol.presentation.view.match.dialog.result_record

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvPlayerRecordItemBinding
import com.yapp.bol.presentation.model.MemberResultItem
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST

class ResultRecordAdapter : ListAdapter<MemberResultItem, ResultRecordAdapter.GameResultViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder {
        val binding =
            RvPlayerRecordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GameResultViewHolder(
        private val binding: RvPlayerRecordItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberResultItem) {
            setImageView(item)
            setTextView(item)
        }

        private fun setImageView(item: MemberResultItem) {
            val image = if (item.role == GUEST) R.drawable.img_dice_empty_small else R.mipmap.ic_member_level
            binding.ivMemberLevelIcon.setImageResource(image)
        }

        private fun setTextView(item: MemberResultItem) {
            val rank = item.rank + 1
            binding.tvMemberRank.text = rank.toString()
            binding.tvMemberName.text = item.nickname
            binding.tvMemberScore.text = item.score.toString()
            val textColor = if (item.rank == 0) Color.parseColor("#FF4D0D") else Color.BLACK
            binding.tvMemberRank.setTextColor(textColor)
            binding.tvMemberScore.setTextColor(textColor)
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
