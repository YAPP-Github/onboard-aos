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
        } else {
            UserRankItemAfter4ViewHolder.create(parent)
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is UserRankUiModel.UserRank1to3 ->
                    (holder as UserRankItem1to3ViewHolder).bind(uiModel.userRankItemList)

                is UserRankUiModel.UserRankAfter4 ->
                    (holder as UserRankItemAfter4ViewHolder).bind(uiModel.userRankItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserRankUiModel.UserRank1to3 -> R.layout.item_rank_1_to_3
            is UserRankUiModel.UserRankAfter4 -> R.layout.item_rank_after_4
            else -> throw UnsupportedOperationException("Unknown user rank view")
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<UserRankUiModel>() {
            override fun areItemsTheSame(oldItem: UserRankUiModel, newItem: UserRankUiModel): Boolean {
                return when (oldItem) {
                    is UserRankUiModel.UserRank1to3 -> {
                        newItem is UserRankUiModel.UserRank1to3 &&
                            oldItem.userRankItemList.size == newItem.userRankItemList.size &&
                            oldItem.userRankItemList.indices.all { index ->
                                oldItem.userRankItemList[index].id == newItem.userRankItemList[index].id
                            }
                    }

                    is UserRankUiModel.UserRankAfter4 -> {
                        newItem is UserRankUiModel.UserRankAfter4 &&
                            oldItem.userRankItem.id == newItem.userRankItem.id
                    }
                }
            }

            override fun areContentsTheSame(oldItem: UserRankUiModel, newItem: UserRankUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
