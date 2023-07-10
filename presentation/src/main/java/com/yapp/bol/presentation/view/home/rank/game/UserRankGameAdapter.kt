package com.yapp.bol.presentation.view.home.rank.game

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yapp.bol.presentation.model.GameItemWithSelected

class UserRankGameAdapter(
    private val onClick: (position: Int, gameId: Long) -> Unit
) : ListAdapter<GameItemWithSelected, UserRankGameViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRankGameViewHolder =
        UserRankGameViewHolder.create(parent, onClick)

    override fun onBindViewHolder(holder: UserRankGameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<GameItemWithSelected>() {
            override fun areItemsTheSame(oldItem: GameItemWithSelected, newItem: GameItemWithSelected): Boolean {
                return oldItem.gameItem.id == newItem.gameItem.id
            }

            override fun areContentsTheSame(oldItem: GameItemWithSelected, newItem: GameItemWithSelected): Boolean {
                return oldItem == newItem
            }
        }
    }
}
