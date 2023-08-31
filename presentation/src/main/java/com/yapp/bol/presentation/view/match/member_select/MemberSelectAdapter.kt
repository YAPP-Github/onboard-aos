package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.designsystem.R as DR
import com.yapp.bol.presentation.databinding.RvMemberSelectItemBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST

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
            binding.btnMemberDelete.setOnClickListener {
                memberDeleteClickListener(item)
            }

            binding.root.setOnClickListener {
                memberDeleteClickListener(item)
            }
        }

        private fun setImageView(item: MemberInfo) {
            val image = if (item.role == GUEST) DR.drawable.img_dice_empty_large else DR.drawable.img_dice
            binding.ivMemberLevel.setImageResource(image)
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
