package com.yapp.bol.presentation.view.group.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.data.GroupSearchUiModel

class GroupListAdapter : PagingDataAdapter<GroupSearchUiModel, RecyclerView.ViewHolder>(GROUP_LIST_COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is GroupSearchUiModel.GroupList -> (holder as GroupListViewHolder).bind(uiModel.groupItem)
                is GroupSearchUiModel.GroupNotFound -> (holder as GroupListNotFoundViewHolder).bind(uiModel.keyword)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_group_list) {
            GroupListViewHolder.create(parent)
        } else {
            GroupListNotFoundViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GroupSearchUiModel.GroupList -> R.layout.item_group_list
            is GroupSearchUiModel.GroupNotFound -> R.layout.item_group_list_not_found
            // when 사용을 위해 else 넣어주었습니다.
            else -> throw UnsupportedOperationException("Unknown Group List View")
        }
    }

    companion object {
        private val GROUP_LIST_COMPARATOR = object : DiffUtil.ItemCallback<GroupSearchUiModel>() {
            override fun areItemsTheSame(oldItem: GroupSearchUiModel, newItem: GroupSearchUiModel): Boolean {
                // data not found의 경우 다 다른 item으로 인식
                // group list의 경우에는 id 비교해서 같은 id일 경우 같은 아이템으로 인식
                return (
                    oldItem is GroupSearchUiModel.GroupList && newItem is GroupSearchUiModel.GroupList &&
                        oldItem.groupItem.id == newItem.groupItem.id
                    )
            }

            override fun areContentsTheSame(oldItem: GroupSearchUiModel, newItem: GroupSearchUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
