package com.yapp.bol.presentation.view.home.rank.game

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yapp.bol.presentation.R
import com.yapp.bol.presentation.model.HomeGameItemUiModel

class UserRankGameAdapter(
    private val onClick: (position: Int, gameId: Long) -> Unit,
    private val scrollAnimation: () -> Unit,
) : ListAdapter<HomeGameItemUiModel, RecyclerView.ViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == R.layout.item_rank_game_list) {
            UserRankGameViewHolder.create(parent, onClick, scrollAnimation)
        } else {
            UserRankGamePaddingViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is HomeGameItemUiModel.GameItem -> {
                    if (uiModel.item.isSelected) {
                        (holder as UserRankGameViewHolder).showGameItemBySelectState(true)
                    } else { (holder as UserRankGameViewHolder).showGameItemBySelectState(false) }

                    holder.bind(uiModel.item)
                }

                is HomeGameItemUiModel.Padding ->
                    (holder as UserRankGamePaddingViewHolder).bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeGameItemUiModel.GameItem -> R.layout.item_rank_game_list
            is HomeGameItemUiModel.Padding -> R.layout.item_rank_game_padding
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<HomeGameItemUiModel>() {
            override fun areItemsTheSame(oldItem: HomeGameItemUiModel, newItem: HomeGameItemUiModel): Boolean {
                return when (oldItem) {
                    is HomeGameItemUiModel.GameItem -> {
                        newItem is HomeGameItemUiModel.GameItem &&
                            oldItem.item.gameItem.id == newItem.item.gameItem.id
                    }

                    is HomeGameItemUiModel.Padding -> {
                        newItem is HomeGameItemUiModel.Padding
                    }
                }
            }

            override fun areContentsTheSame(oldItem: HomeGameItemUiModel, newItem: HomeGameItemUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
