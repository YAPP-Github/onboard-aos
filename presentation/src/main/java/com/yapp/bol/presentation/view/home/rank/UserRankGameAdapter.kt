package com.yapp.bol.presentation.view.home.rank

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yapp.bol.domain.model.GameItem

class UserRankGameAdapter : ListAdapter<GameItem, UserRankGameViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRankGameViewHolder =
        UserRankGameViewHolder.create(parent)

    override fun onBindViewHolder(holder: UserRankGameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diff = object : DiffUtil.ItemCallback<GameItem>() {
            override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
