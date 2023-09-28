package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvMemberSelectItemBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST
import com.yapp.bol.designsystem.R as DR

class MemberSelectAdapter(
    private val memberDeleteClickListener: (MemberInfo) -> Unit,
) : ListAdapter<MemberInfo, MemberSelectAdapter.MemberSelectViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberSelectViewHolder {
        val binding =
            RvMemberSelectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberSelectViewHolder(binding, memberDeleteClickListener)
    }

    override fun onBindViewHolder(holder: MemberSelectViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MemberSelectViewHolder(
        private val binding: RvMemberSelectItemBinding,
        private val memberDeleteClickListener: (MemberInfo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberInfo) {
            binding.tvMemberName.text = item.nickname
            setImageView(item)
            binding.viewMe.root.isVisible = item.isMe
            binding.btnMemberDelete.setOnClickListener {
                memberDeleteClickListener(item)
            }

            binding.root.setOnClickListener {
                memberDeleteClickListener(item)
            }
        }

        private fun setImageView(item: MemberInfo) {
            val imageRes = if (item.role == GUEST) {
                binding.ivMemberLevel.setPadding(10, 10, 10, 10)
                DR.drawable.img_dice_empty
            } else {
                binding.ivMemberLevel.setPadding(0, 0, 0, 0)
                DR.drawable.img_dice
            }
            binding.ivMemberLevel.setImageResource(imageRes)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<MemberInfo>() {
            override fun areItemsTheSame(oldItem: MemberInfo, newItem: MemberInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MemberInfo, newItem: MemberInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
