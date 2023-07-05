package com.yapp.bol.presentation.view.home.rank.group_info

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.model.DrawerGroupInfoUiModel

class DrawerGroupInfoAdapter : ListAdapter<DrawerGroupInfoUiModel, RecyclerView.ViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == R.layout.item_group_info_detail) {
            DrawerCurrentGroupInfoViewHolder.create(parent)
        } else {
            DrawerOtherGroupViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is DrawerGroupInfoUiModel.CurrentGroupInfo ->
                    (holder as DrawerCurrentGroupInfoViewHolder).bind(uiModel.groupDetailItem)
                is DrawerGroupInfoUiModel.OtherGroupInfo ->
                    (holder as DrawerOtherGroupViewHolder).bind(uiModel.joinedGroupItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DrawerGroupInfoUiModel.CurrentGroupInfo -> R.layout.item_group_info_detail
            is DrawerGroupInfoUiModel.OtherGroupInfo -> R.layout.item_group_info_group_name
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<DrawerGroupInfoUiModel>() {
            override fun areItemsTheSame(oldItem: DrawerGroupInfoUiModel, newItem: DrawerGroupInfoUiModel): Boolean {
                return when (oldItem) {
                    is DrawerGroupInfoUiModel.CurrentGroupInfo -> {
                        newItem is DrawerGroupInfoUiModel.CurrentGroupInfo &&
                            oldItem.groupDetailItem.id == newItem.groupDetailItem.id
                    }

                    is DrawerGroupInfoUiModel.OtherGroupInfo -> {
                        newItem is DrawerGroupInfoUiModel.OtherGroupInfo &&
                            oldItem.joinedGroupItem.id == newItem.joinedGroupItem.id
                    }
                }
            }

            override fun areContentsTheSame(oldItem: DrawerGroupInfoUiModel, newItem: DrawerGroupInfoUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
