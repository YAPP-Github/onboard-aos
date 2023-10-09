package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvMemberSelectItemBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.utils.setDiceImageForRole

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
            binding.ivMemberLevel.setDiceImageForRole(item.role)
            binding.btnMemberDelete.setOnClickListener {
                memberDeleteClickListener(item)
            }

            binding.root.setOnClickListener {
                memberDeleteClickListener(item)
            }
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
