package com.yapp.bol.presentation.view.home.rank.user

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.model.UserRankUiModel

class UserRankAdapter : ListAdapter<UserRankUiModel, RecyclerView.ViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == R.layout.item_rank_1_to_3) {
            UserRankItem1to3ViewHolder.create(parent)
        } else if (viewType == R.layout.item_rank_after_4) {
            UserRankItemAfter4ViewHolder.create(parent)
        } else {
            UserRankPaddingViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is UserRankUiModel.UserRank1to3 ->
                    (holder as UserRankItem1to3ViewHolder).bind(uiModel.itemList)

                is UserRankUiModel.UserRankAfter4 ->
                    (holder as UserRankItemAfter4ViewHolder).bind(uiModel.item)

                is UserRankUiModel.UserRankPadding ->
                    (holder as UserRankPaddingViewHolder)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserRankUiModel.UserRank1to3 -> R.layout.item_rank_1_to_3
            is UserRankUiModel.UserRankAfter4 -> R.layout.item_rank_after_4
            is UserRankUiModel.UserRankPadding -> R.layout.item_rank_padding
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<UserRankUiModel>() {
            override fun areItemsTheSame(oldItem: UserRankUiModel, newItem: UserRankUiModel): Boolean {
                return when (oldItem) {
                    is UserRankUiModel.UserRank1to3 -> {
                        newItem is UserRankUiModel.UserRank1to3 &&
                            oldItem.itemList.size == newItem.itemList.size &&
                            oldItem.itemList.indices.all { index ->
                                oldItem.itemList[index].userRankItem.id ==
                                    newItem.itemList[index].userRankItem.id
                            }
                    }

                    is UserRankUiModel.UserRankAfter4 -> {
                        newItem is UserRankUiModel.UserRankAfter4 &&
                            oldItem.item.userRankItem.id == newItem.item.userRankItem.id
                    }

                    is UserRankUiModel.UserRankPadding -> true
                }
            }

            override fun areContentsTheSame(oldItem: UserRankUiModel, newItem: UserRankUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
