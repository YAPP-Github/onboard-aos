package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.RvMemberItemBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST

class MembersAdapter(
    private val memberClickListener: (MemberInfo, Int, Boolean) -> Unit,
) : ListAdapter<MemberInfo, MembersAdapter.MembersViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val binding =
            RvMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MembersViewHolder(binding, memberClickListener)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    class MembersViewHolder(
        val binding: RvMemberItemBinding,
        private val memberClickListener: (MemberInfo, Int, Boolean) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberInfo, position: Int) {
            binding.tvMemberName.text = item.nickname
            binding.cbMemberSelect.isChecked = item.isChecked
            setImageView(item)
            setClickListener(item, position)
        }

        private fun setImageView(item: MemberInfo) {
            val image = if (item.role == GUEST) R.drawable.img_dice_empty_small else R.mipmap.ic_member_level
            binding.ivMemberLevelIcon.setImageResource(image)
        }

        private fun setClickListener(item: MemberInfo, position: Int) {
            binding.root.setOnClickListener {
                binding.cbMemberSelect.isChecked = binding.cbMemberSelect.isChecked.not()
                memberClickListener(item, position, binding.cbMemberSelect.isChecked)
            }
            binding.cbMemberSelect.setOnClickListener {
                memberClickListener(item, position, binding.cbMemberSelect.isChecked)
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
