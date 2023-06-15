package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvMemberItemBinding
import com.yapp.bol.presentation.model.MemberItem

class MembersAdapter(
    private val memberClickListener: (MemberItem, Boolean) -> Unit,
) : ListAdapter<MemberItem, MembersAdapter.MembersViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val binding =
            RvMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MembersViewHolder(binding, memberClickListener)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class MembersViewHolder(
        val binding: RvMemberItemBinding,
        private val memberClickListener: (MemberItem, Boolean) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberItem) {
            binding.tvMemberName.text = item.name
            binding.cbMemberSelect.isChecked = item.isChecked
            setClickListener(item)
        }

        private fun setClickListener(item: MemberItem) {
            binding.cbMemberSelect.setOnClickListener {
                item.isChecked = binding.cbMemberSelect.isChecked
                memberClickListener(item, binding.cbMemberSelect.isChecked)
            }
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<MemberItem>() {
            override fun areItemsTheSame(oldItem: MemberItem, newItem: MemberItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MemberItem, newItem: MemberItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
