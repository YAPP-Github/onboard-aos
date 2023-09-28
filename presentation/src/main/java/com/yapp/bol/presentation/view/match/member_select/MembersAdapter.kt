package com.yapp.bol.presentation.view.match.member_select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.designsystem.R as DR
import com.yapp.bol.presentation.databinding.RvMemberItemBinding
import com.yapp.bol.presentation.model.MemberInfo
import com.yapp.bol.presentation.utils.config.setDice
import com.yapp.bol.presentation.view.match.MatchActivity.Companion.GUEST

class MembersAdapter(
    private val memberClickListener: (MemberInfo, Int, Boolean) -> Unit,
    private val checkedMaxPlayer: () -> Boolean,
) : ListAdapter<MemberInfo, MembersAdapter.MembersViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val binding =
            RvMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MembersViewHolder(binding, memberClickListener, checkedMaxPlayer)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    class MembersViewHolder(
        val binding: RvMemberItemBinding,
        private val memberClickListener: (MemberInfo, Int, Boolean) -> Unit,
        private val checkedMaxPlayer: () -> Boolean,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberInfo, position: Int) {
            binding.tvMemberName.text = item.nickname
            binding.cbMemberSelect.isChecked = item.isChecked
            binding.ivMemberLevelIcon.setDice(item.role)
            setClickListener(item, position)
        }

        private fun setClickListener(item: MemberInfo, position: Int) {
            binding.root.setOnClickListener {
                if (checkedMaxPlayer() || binding.cbMemberSelect.isChecked) {
                    binding.cbMemberSelect.isChecked = binding.cbMemberSelect.isChecked.not()
                    memberClickListener(item, position, binding.cbMemberSelect.isChecked)
                }
            }

            binding.cbMemberSelect.setOnClickListener {
                if (checkedMaxPlayer().not() && binding.cbMemberSelect.isChecked) {
                    binding.cbMemberSelect.isChecked = item.isChecked
                } else {
                    memberClickListener(item, position, binding.cbMemberSelect.isChecked)
                }
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
