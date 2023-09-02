package com.yapp.bol.presentation.view.group.dialog.guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.designsystem.R
import com.yapp.bol.presentation.databinding.RvMemberItemBinding
import com.yapp.bol.presentation.model.MemberInfo

class GuestListAdapter(
    private val isEnableButton: (Boolean, String) -> Unit
) : ListAdapter<MemberInfo, GuestListAdapter.GuestViewHolder>(diff) {

    var selectGuest: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val binding =
            RvMemberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestViewHolder(binding, ::updateSelectedGuest)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    private fun updateSelectedGuest(target: Int) {
        if (selectGuest == target || selectGuest == null) {
            val check = getItem(target).isChecked
            getItem(target).isChecked = check.not()
            selectGuest = if (check) null else target
            notifyItemChanged(target)
        } else {
            val prev = selectGuest ?: return
            swipeSelectedGuest(prev, target)
        }
        isEnableButton(
            selectGuest != null,
            if (selectGuest == null) "" else getItem(selectGuest ?: return).nickname
        )
    }

    private fun swipeSelectedGuest(prev: Int, target: Int) {
        getItem(target).isChecked = true
        getItem(prev).isChecked = false
        notifyItemChanged(target)
        notifyItemChanged(prev)
        selectGuest = target
    }

    class GuestViewHolder(
        private val binding: RvMemberItemBinding,
        private val updateSelectedGuest: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MemberInfo, position: Int) {
            binding.tvMemberName.text = item.nickname
            binding.cbMemberSelect.isChecked = item.isChecked
            setImageView(item)
            setClickListener(position)
        }

        private fun setImageView(item: MemberInfo) {
            val image = if (item.isChecked.not()) R.drawable.img_dice_empty_small else R.drawable.img_dice
            binding.ivMemberLevelIcon.setImageResource(image)
        }

        private fun setClickListener(position: Int) = with(binding) {
            root.setOnClickListener { updateSelectedGuest(position) }
            cbMemberSelect.setOnClickListener { updateSelectedGuest(position) }
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
