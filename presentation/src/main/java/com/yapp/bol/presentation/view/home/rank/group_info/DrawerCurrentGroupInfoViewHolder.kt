package com.yapp.bol.presentation.view.home.rank.group_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupInfoDetailBinding
import com.yapp.bol.presentation.utils.loadImage

class DrawerCurrentGroupInfoViewHolder(
    private val binding: ItemGroupInfoDetailBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joinedGroupItem: JoinedGroupItem) {
        binding.apply {
            ivGroup.loadImage(joinedGroupItem.profileImageUrl)
            tvGroupDescription.text = joinedGroupItem.description
            tvGroupOrganization.text = joinedGroupItem.organization
        }
    }

    companion object {
        fun create(parent: ViewGroup): DrawerCurrentGroupInfoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_group_info_detail, parent, false)
            val binding = ItemGroupInfoDetailBinding.bind(view)
            return DrawerCurrentGroupInfoViewHolder(binding)
        }
    }
}
