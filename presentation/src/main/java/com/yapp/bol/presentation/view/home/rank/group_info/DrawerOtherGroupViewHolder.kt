package com.yapp.bol.presentation.view.home.rank.group_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupInfoGroupNameBinding

class DrawerOtherGroupViewHolder(
    private val binding: ItemGroupInfoGroupNameBinding,
    private val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(joinedGroupItem: JoinedGroupItem) {
        binding.tvGroupName.text = joinedGroupItem.name
        binding.root.setOnClickListener {
            onClick(joinedGroupItem.id)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (Long) -> Unit
        ): DrawerOtherGroupViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_group_info_group_name, parent, false)
            val binding = ItemGroupInfoGroupNameBinding.bind(view)
            return DrawerOtherGroupViewHolder(binding, onClick)
        }
    }
}
