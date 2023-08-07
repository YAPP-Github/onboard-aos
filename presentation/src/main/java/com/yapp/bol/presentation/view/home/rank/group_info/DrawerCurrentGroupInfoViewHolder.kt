package com.yapp.bol.presentation.view.home.rank.group_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.GroupDetailItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupInfoDetailBinding
import com.yapp.bol.presentation.utils.loadImage

class DrawerCurrentGroupInfoViewHolder(
    private val binding: ItemGroupInfoDetailBinding,
    private val onClick: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(groupDetailItem: GroupDetailItem) {
        binding.apply {
            bindData(groupDetailItem)
            setOnClickListener(groupDetailItem)
        }
    }

    private fun ItemGroupInfoDetailBinding.bindData(groupDetailItem: GroupDetailItem) {
        ivGroup.loadImage(groupDetailItem.profileImageUrl, 8)
        tvGroupDescription.text = groupDetailItem.description
        tvGroupOrganization.text = groupDetailItem.organization
        tvMemberCount.text = groupDetailItem.memberCount.toString()
        tvManager.text = groupDetailItem.owner.nickname
        tvCode.text = groupDetailItem.accessCode
    }

    private fun ItemGroupInfoDetailBinding.setOnClickListener(groupDetailItem: GroupDetailItem) {
        btnCopy.setOnClickListener { onClick(groupDetailItem.accessCode) }
        tvCode.setOnClickListener { onClick(groupDetailItem.accessCode) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (String) -> Unit,
        ): DrawerCurrentGroupInfoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_group_info_detail, parent, false)
            val binding = ItemGroupInfoDetailBinding.bind(view)
            return DrawerCurrentGroupInfoViewHolder(binding, onClick)
        }
    }
}
