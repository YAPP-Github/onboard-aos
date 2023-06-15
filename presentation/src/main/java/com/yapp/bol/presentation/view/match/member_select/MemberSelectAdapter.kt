package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.databinding.RvMemberSelectItemBinding
import com.yapp.bol.presentation.model.MemberItem
import okhttp3.internal.notifyAll

class MemberSelectAdapter(
    private val memberDeleteClickListener: (MemberItem) -> Unit,
) : ListAdapter<MemberItem, MemberSelectAdapter.MemberSelectViewHolder>(diff) {

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
        private val memberDeleteClickListener: (MemberItem) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberItem) {
            binding.tvMemberName.text = item.name
            binding.btnMemberDelete.setOnClickListener {
                memberDeleteClickListener(item)
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
