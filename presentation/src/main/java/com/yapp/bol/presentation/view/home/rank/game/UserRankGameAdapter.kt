package com.yapp.bol.presentation.view.home.rank.game

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.yapp.bol.domain.model.GameItem

class UserRankGameAdapter : ListAdapter<GameItem, UserRankGameViewHolder>(diff) {

    private var selectedPosition: Int = 0
    private lateinit var onClick: (Int) -> Unit

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }

    fun getSelectedPosition(): Int = selectedPosition

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRankGameViewHolder =
        UserRankGameViewHolder.create(parent, onClick)

    override fun onBindViewHolder(holder: UserRankGameViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position == selectedPosition)
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
