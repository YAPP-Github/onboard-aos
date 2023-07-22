package com.yapp.bol.presentation.view.home.rank.group_info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.domain.model.JoinedGroupItem
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.databinding.ItemGroupInfoGroupNameBinding

class DrawerOtherGroupViewHolder(
    private val binding: ItemGroupInfoGroupNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        // TODO: Fragment 새로 생성 후..? or 현재거 갈아치우기?
    }

    fun bind(joinedGroupItem: JoinedGroupItem) {
        binding.tvGroupName.text = joinedGroupItem.name
    }

    companion object {
        fun create(parent: ViewGroup): DrawerOtherGroupViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.item_group_info_group_name, parent, false)
            val binding = ItemGroupInfoGroupNameBinding.bind(view)
            return DrawerOtherGroupViewHolder(binding)
        }
    }
}
