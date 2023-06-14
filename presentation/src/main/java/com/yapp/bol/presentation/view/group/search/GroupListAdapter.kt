package com.yapp.bol.presentation.view.group.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.data.UiModel

class GroupListAdapter : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(GROUP_LIST_COMPARATOR) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is UiModel.GroupList -> (holder as GroupListViewHolder).bind(uiModel.groupSearchItem)
                // TODO BEFORE PR : text input 바꿔주기
                is UiModel.DataNotFound -> (holder as GroupListNotFoundViewHolder).bind("")
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
            is UiModel.GroupList -> R.layout.item_group_list
            is UiModel.DataNotFound -> R.layout.item_group_list_not_found
            // when 사용을 위해 else 넣어주었습니다.
            else -> throw UnsupportedOperationException("Unknown Group List View")
        }
    }

    companion object {
        private val GROUP_LIST_COMPARATOR = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                // data not found의 경우 다 다른 item으로 인식
                // group list의 경우에는 id 비교해서 같은 id일 경우 같은 아이템으로 인식
                return (
                    oldItem is UiModel.GroupList && newItem is UiModel.GroupList &&
                        oldItem.groupSearchItem.id == newItem.groupSearchItem.id
                    )
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
